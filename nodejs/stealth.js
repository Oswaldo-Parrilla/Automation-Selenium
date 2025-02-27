const puppeteer = require('puppeteer-extra');
const StealthPlugin = require('puppeteer-extra-plugin-stealth');

// Usa el plugin Stealth
puppeteer.use(StealthPlugin());

(async () => {
    const browser = await puppeteer.launch({ 
        headless: false, // headless: false para ver el navegador
        args: ['--remote-debugging-port=9222'] // Habilita el puerto de depuración
    });

    const page = await browser.newPage();

    // Configura un User-Agent realista
    await page.setUserAgent('user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.6834.111 Safari/537.36');

console.log("WebSocket Debugging URL:", browser.wsEndpoint()); // <-- Agregamos esta línea

    // Navega a una página
    await page.goto('https://www.google.com');

    // Imprime el WebSocket Debugging URL
    const wsEndpoint = browser.wsEndpoint();
    console.log(wsEndpoint); // Esto es importante para Selenium

     // Espera 5 segundos usando setTimeout
    await new Promise(resolve => setTimeout(resolve, 5000));

    // Cierra el navegador
    await browser.close();
})();