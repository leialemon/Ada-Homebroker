Set-Location -Path "..\miniFront"

Start-Process "chrome.exe" -ArgumentList "index.html"

Set-Location -Path "..\homebroker\target"

Start-Process "java.exe" "-jar homebroker-0.0.1-SNAPSHOT.jar"
