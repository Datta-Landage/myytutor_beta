# Get WhatsApp Group ID
# Run this after creating your WhatsApp group

$PHONE_ID = "910129395508301"
$TOKEN = "EAASAlPk43b0BP0Xk2xh1VWE3oZBlO6Yfvv5Txcym2SOxTh25TJFS2nGaVIe5ZABTH5p11hHX5vs8EwEoARgBP3fiVt8JhCLZCKV8ioIkcKC4ZwPK0JqZCZADDubF5kDP0XjTZA8STl6JhG53B46FG5m8VPQpiZB2LBqRsvSTtt0InpQmXwgDPi4k1sR76FXuHk9BbNCxFCaWeKN2162CrOgUJOJg4xAXHsKEn"

Write-Host "🔍 Fetching your WhatsApp groups..." -ForegroundColor Cyan
Write-Host ""

try {
    $url = "https://graph.facebook.com/v18.0/$PHONE_ID/whatsapp_business_groups"
    $headers = @{
        "Authorization" = "Bearer $TOKEN"
    }
    
    $response = Invoke-RestMethod -Uri $url -Headers $headers -Method Get
    
    if ($response.data -and $response.data.Count -gt 0) {
        Write-Host "✅ Found $($response.data.Count) group(s):" -ForegroundColor Green
        Write-Host ""
        
        foreach ($group in $response.data) {
            Write-Host "📱 Group Name: $($group.name)" -ForegroundColor Yellow
            Write-Host "🆔 Group ID: $($group.id)" -ForegroundColor Green
            Write-Host ""
            Write-Host "👉 Copy this ID to your .env.dev file:" -ForegroundColor Cyan
            Write-Host "   WHATSAPP_COMMUNITY_ID=$($group.id)" -ForegroundColor White
            Write-Host ""
            Write-Host "─────────────────────────────────────────────" -ForegroundColor DarkGray
            Write-Host ""
        }
    } else {
        Write-Host "⚠️ No groups found!" -ForegroundColor Yellow
        Write-Host ""
        Write-Host "Make sure you:" -ForegroundColor White
        Write-Host "1. Created a WhatsApp group on your phone" -ForegroundColor White
        Write-Host "2. Added at least 1 contact to the group" -ForegroundColor White
        Write-Host "3. The group was created with the same WhatsApp Business Account" -ForegroundColor White
        Write-Host ""
        Write-Host "If you just created the group, wait 1-2 minutes and try again." -ForegroundColor Cyan
    }
    
} catch {
    Write-Host "❌ Error fetching groups:" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
    Write-Host ""
    Write-Host "Possible issues:" -ForegroundColor Yellow
    Write-Host "1. Access token expired (generate a new one)" -ForegroundColor White
    Write-Host "2. Phone number ID is incorrect" -ForegroundColor White
    Write-Host "3. No groups exist yet (create one on your phone first)" -ForegroundColor White
}

Write-Host ""
Write-Host "Press any key to exit..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
