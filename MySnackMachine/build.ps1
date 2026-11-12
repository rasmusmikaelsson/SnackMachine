# Kör i PowerShell från projektets rot: .\build.ps1
Set-Location $PSScriptRoot
New-Item -ItemType Directory -Force -Path out | Out-Null
$sources = @(Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
& javac -encoding UTF-8 -d out $sources
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
& jar cfe MySnackMachine.jar se.umu.cs.snackmachine.MySnackMachine -C out .
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
Write-Host "Klart: java -jar MySnackMachine.jar"
