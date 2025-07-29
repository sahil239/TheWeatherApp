# 🌤️ The Weather App

A simple Android app built with Kotlin, Jetpack Compose, and MVVM architecture that displays current and weekly weather forecasts using the OpenWeatherMap API.

---

## 🚀 Getting Started

### 🔑 API Key Setup

To fetch weather data, this app uses the OpenWeatherMap API. You need to provide your own API key by following these steps:

1. **Get Your API Key**
    - Sign up at [https://openweathermap.org/api](https://openweathermap.org/api)
    - Go to your profile → API Keys → Generate a new key.

2. **Add the Key to `local.properties`**
    - Open your project's `local.properties` file (located in the root directory).
    - Add the following line (replace `your_api_key_here` with your actual key):

      ```
      OPEN_WEATHER_API_KEY=your_api_key_here
      ```

3. **Use the Key in Build Config**
    - The project is set up to read `OPEN_WEATHER_API_KEY` from `local.properties` and include it in the `BuildConfig` automatically via `build.gradle`.

---

## 📦 Tech Stack

- Jetpack Compose
- MVVM (Use Case Driven)
- Dagger Hilt
- Retrofit
- Room (for offline caching)

---

## 💡 Features

- Location permission handling
- Realtime weather & forecast
- Offline access with Room caching
- Elegant UI with Compose
- BottomSheet for detailed view

---

## 📸 Preview

*(Insert screenshots here)*
<table>
  <tr>
    <td>
      <img src="/screenshots/list.png" height="2400" alt="Android Demo"/>
    </td>
    <td>
      <img src="/screenshots/details.png" height="2400" alt="iOS Demo"/>
    </td>
  </tr>
</table>
---

## 🧪 Testing

The project includes unit tests for `ViewModel` and `Repository` layers. Run tests via:
      ```
      ./gradlew testDebugUnitTest
      ```
