# Flynas Web UI Parallel Automation Framework

An enterprise-grade, highly scalable Data-Driven UI Automation Testing Platform designed for the Flynas booking ecosystem. This architecture leverages runtime Java Reflection to match dynamically managed test parameters with individual TestNG execution blocks.

## 🚀 Architectural Highlights & Core Capabilities
- **Thread-Safe Parallel Execution:** Implements a strict `ThreadLocal<WebDriver>` multi-threaded execution strategy, enabling independent, parallel browser invocations with zero cross-thread pollution.
- **Dynamic Reflection-Driven Testing:** Integrates a zero-maintenance `@DataProvider` mapping engine using Java Reflection (`Method.getName()`) to dynamically tie specific Excel rows (`TC-01`, `TC-02`) to test methods based on naming conventions.
- **Environment & Configuration Management:** Features a static properties routing manager (`ConfigReader`) that maps execution contexts (`STAGE`, `PRELIVE`) and browser profiles (`CHROME`, `FIREFOX`, `EDGE`, `HEADLESS`) via command-line arguments.
- **Apache POI Excel Feeder:** Built with safe cell-type mapping wrappers utilizing `DataFormatter` to handle mixed data sets gracefully and keep data entry clear for non-technical stakeholders.
- **Robust Synchronization Layer:** Leverages explicit waits wrapped in reusable `SeleniumUtils` helpers to handle asynchronous page ready states and airline loader elements.

## 🛠️ Technology Stack & Dependencies
- **Core Language:** Java 21
- **Automation Engine:** Selenium WebDriver (v4.x)
- **Test Orchestration:** TestNG
- **Data Processor:** Apache POI (XSSF)
- **Reporting Dashboard:** Allure Reports
- **Logging Infrastructure:** Log4j2
- **Build Automation:** Maven
