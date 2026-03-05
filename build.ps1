param(
    [string]$ImageName = "portfolio-api-local",
    [string]$ContainerName = "portfolio-api-local"
)

$ErrorActionPreference = "Stop"

Set-StrictMode -Version Latest

function Invoke-ExternalCommand {
    param(
        [Parameter(Mandatory = $true)][string]$Executable,
        [Parameter(Mandatory = $true)][string[]]$Arguments,
        [Parameter(Mandatory = $true)][string]$Description
    )

    & $Executable @Arguments

    if (
        $LASTEXITCODE -ne 0
    ) {
        throw "$Description failed with exit code $LASTEXITCODE."
    }
}

$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path

Set-Location $projectRoot

$envFilePath = Join-Path $projectRoot ".env"
$envExampleFilePath = Join-Path $projectRoot ".env.example"

if (
    -not (Test-Path $envFilePath)
) {
    if (
        -not (Test-Path $envExampleFilePath)
    ) {
        throw "Missing .env and .env.example. Create .env with required environment variables."
    }

    Copy-Item $envExampleFilePath $envFilePath
    throw "Created .env from .env.example. Fill required values and rerun build.ps1."
}

Get-Command docker -ErrorAction Stop | Out-Null

$envMap = @{}

foreach ($rawLine in Get-Content $envFilePath) {
    $line = $rawLine.Trim()

    if (
        $line.Length -eq 0 -or $line.StartsWith("#")
    ) {
        continue
    }

    $tokens = $line -split "=", 2

    if (
        $tokens.Length -ne 2
    ) {
        throw "Invalid .env line: '$line'."
    }

    $key = $tokens[0].Trim()
    $value = $tokens[1].Trim()

    if (
        [string]::IsNullOrWhiteSpace($key)
    ) {
        throw "Invalid .env key in line: '$line'."
    }

    $envMap[$key] = $value
}

$requiredKeys = @(
    "SPRING_DATA_MONGODB_URI",
    "SPRING_DATA_MONGODB_DATABASE",
    "SERVER_PORT"
)

foreach ($requiredKey in $requiredKeys) {
    if (
        -not $envMap.ContainsKey($requiredKey)
    ) {
        throw "Missing required environment variable '$requiredKey' in .env."
    }

    if (
        [string]::IsNullOrWhiteSpace($envMap[$requiredKey])
    ) {
        throw "Environment variable '$requiredKey' in .env cannot be empty."
    }
}

if (
    -not $envMap.ContainsKey("SPRING_PROFILES_ACTIVE")
) {
    throw "Missing required environment variable 'SPRING_PROFILES_ACTIVE' in .env."
}

if (
    [string]::IsNullOrWhiteSpace($envMap["SPRING_PROFILES_ACTIVE"])
) {
    throw "Environment variable 'SPRING_PROFILES_ACTIVE' in .env cannot be empty."
}

Invoke-ExternalCommand -Executable ".\\gradlew.bat" -Arguments @("clean", "bootJar") -Description "Gradle bootJar build"

$jarFiles = Get-ChildItem -Path (Join-Path $projectRoot "build\\libs") -Filter "*.jar" | Where-Object {
    $_.Name -notmatch "plain"
}

if (
    $jarFiles.Count -eq 0
) {
    throw "No executable JAR found under build/libs."
}

$jarFile = $jarFiles | Sort-Object LastWriteTime -Descending | Select-Object -First 1
$jarRelativePath = "build/libs/$($jarFile.Name)"

Invoke-ExternalCommand -Executable "docker" -Arguments @("build", "--build-arg", "JAR_FILE=$jarRelativePath", "-t", $ImageName, ".") -Description "Docker image build"

$existingContainer = docker ps -aq --filter "name=^$ContainerName$"

if (
    -not [string]::IsNullOrWhiteSpace($existingContainer)
) {
    Invoke-ExternalCommand -Executable "docker" -Arguments @("rm", "-f", $ContainerName) -Description "Remove existing container"
}

$hostPort = $envMap["SERVER_PORT"]

Invoke-ExternalCommand -Executable "docker" -Arguments @("run", "-d", "--name", $ContainerName, "--restart", "unless-stopped", "--env-file", ".env", "-p", "$hostPort`:`8080", $ImageName) -Description "Run Docker container"

Write-Host "Container '$ContainerName' is running on port $hostPort."