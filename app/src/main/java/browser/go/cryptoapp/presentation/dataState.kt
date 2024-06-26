package browser.go.cryptoapp.presentation

data class dataState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: String = ""
)