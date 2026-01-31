package io.github.shane.thomas.noted.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EditorViewModel : ViewModel() {

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    fun updateTitle(value:String){
        _title.value = value
    }

    private val _text = MutableStateFlow("")
    val text: StateFlow<String> = _text

    fun updateText(value: String) {
        _text.value = value
    }
}
