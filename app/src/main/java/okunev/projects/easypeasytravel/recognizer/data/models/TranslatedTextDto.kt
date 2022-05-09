package okunev.projects.easypeasytravel.recognizer.data.models

import com.google.gson.annotations.SerializedName

class TranslatedTextDto(
    @SerializedName("ok")
    val success: Boolean,
    @SerializedName("text_lang")
    val textLang: String,
    @SerializedName("translated_text")
    val translatedText: String
)