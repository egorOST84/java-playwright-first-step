package org.example;

import com.microsoft.playwright.*;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        // TODO Playwright.CreateOptions options = new Playwright.CreateOptions();
        // Playwright.CreateOptions options = new Playwright.CreateOptions();
        // options.setEnv();
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false)
                    .setSlowMo(1000));
            BrowserContext context = browser.newContext();

            // Start tracing before creating / navigating a page.
            context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(false));

            Page page = context.newPage();
            page.navigate("https://demo.playwright.dev/todomvc/");
//            page.navigate("https://demo.playwright.dev/todomvc/#/");
            page.getByPlaceholder("What needs to be done?").click();
            page.getByPlaceholder("What needs to be done?").fill("Hello-world");
            page.getByPlaceholder("What needs to be done?").press("Enter");

            // Stop tracing and export it into a zip archive.
            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get("trace.zip")));
        }
    }
}