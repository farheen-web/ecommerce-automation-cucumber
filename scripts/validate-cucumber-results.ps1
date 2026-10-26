$ErrorActionPreference = "Stop"

$reportDirectory = Join-Path (Get-Location) "target\surefire-reports"

if (-not (Test-Path $reportDirectory)) {
    Write-Error "Surefire report directory not found: $reportDirectory"
    exit 1
}

$reports = Get-ChildItem -Path $reportDirectory -Filter "TEST-*.xml" -File

if (-not $reports) {
    Write-Error "No Surefire XML test reports were found."
    exit 1
}

$executed = 0
$skipped = 0
$failures = 0
$errors = 0

foreach ($report in $reports) {
    [xml]$xml = Get-Content -Path $report.FullName -Raw

    foreach ($testCase in $xml.SelectNodes("//testcase")) {
        if ($testCase.SelectSingleNode("skipped")) {
            $skipped++
            continue
        }

        $executed++

        if ($testCase.SelectSingleNode("failure")) {
            $failures++
        }

        if ($testCase.SelectSingleNode("error")) {
            $errors++
        }
    }
}

Write-Host "========== CUCUMBER RESULT VALIDATION =========="
Write-Host "Executed scenarios: $executed"
Write-Host "Skipped scenarios:  $skipped"
Write-Host "Failures:           $failures"
Write-Host "Errors:             $errors"
Write-Host "================================================"

if ($executed -eq 0) {
    Write-Error "No scenarios executed. Failing the Jenkins build."
    exit 1
}

if ($failures -gt 0 -or $errors -gt 0) {
    Write-Error "Test failures or errors detected."
    exit 1
}

Write-Host "Cucumber result validation passed."
exit 0
