package com.example.screens

import android.app.Activity
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.net.Uri
import android.speech.RecognizerIntent
import com.example.BuildConfig
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import java.util.Locale
import java.text.NumberFormat
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.WorkOutline
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.auth.AuthProvider
import com.example.assistant.AssistantAiClient
import com.example.assistant.AssistantInterpretation
import com.example.R
import com.example.dashboard.ActivityLog
import com.example.dashboard.DashboardViewModel
import com.example.dashboard.EmployeeActivity
import com.example.dashboard.SalesOrder
import com.example.dashboard.Department
import com.example.dashboard.LabourCategory
import com.example.dashboard.LabourAssignment
import com.example.reports.ReportExporter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import androidx.core.content.ContextCompat
import kotlin.math.roundToInt
import kotlin.math.ceil

enum class GlassThemeStyle {
    ROYAL_GLASS,
    AURORA_GLASS
}

private enum class AppLanguage(val tag: String, val nativeName: String) {
    ENGLISH("en", "English"),
    TAMIL("ta", "தமிழ்"),
    HINDI("hi", "हिन्दी"),
    TELUGU("te", "తెలుగు"),
    KANNADA("kn", "ಕನ್ನಡ"),
    MALAYALAM("ml", "മലയാളം")
}

private val activeOperationalLanguage = mutableStateOf(AppLanguage.ENGLISH)

private fun uiText(language: AppLanguage, key: String): String {
    val translations = when (language) {
        AppLanguage.ENGLISH -> emptyMap()
        AppLanguage.TAMIL -> mapOf(
            "terminal" to "நேரடி தொழிலாளர் மற்றும் அளவீட்டு மையம்", "supervisor" to "மேற்பார்வையாளர்",
            "account" to "கணக்கு மற்றும் விருப்பங்கள்", "online" to "ஆன்லைன்", "email" to "மின்னஞ்சல்",
            "language" to "பயன்பாட்டு மொழி", "languageHint" to "பயன்பாடு மற்றும் பேச்சுக்கான மொழி",
            "theme" to "தோற்றம்", "reports" to "அறிக்கை சேமிப்பு இடம்", "changeFolder" to "கோப்புறையை மாற்று",
            "logout" to "வெளியேறு", "home" to "முகப்பு", "master" to "முதன்மை", "report" to "அறிக்கை",
            "labourRouting" to "தொழிலாளர் ஒதுக்கீடு மற்றும் வழிப்படுத்தல்", "labourRoutingSub" to "நேரடி பணியாளர் ஒதுக்கீடு, வேலைகள் மற்றும் பணிநேரத்தை கண்காணிக்கவும்.",
            "confirmLogout" to "வெளியேறுவதை உறுதிசெய்யவும்", "logoutQuestion" to "செயலில் உள்ள மேற்பார்வையாளர் அமர்விலிருந்து வெளியேற வேண்டுமா? உள்ளூர் தரவு பாதுகாப்பாக இருக்கும்.",
            "confirm" to "வெளியேறு", "cancel" to "ரத்து"
        )
        AppLanguage.HINDI -> mapOf(
            "terminal" to "लाइव श्रम और मेट्रिक्स टर्मिनल", "supervisor" to "पर्यवेक्षक",
            "account" to "खाता और प्राथमिकताएँ", "online" to "ऑनलाइन", "email" to "ईमेल",
            "language" to "ऐप की भाषा", "languageHint" to "ऐप और वाणी की भाषा",
            "theme" to "थीम", "reports" to "रिपोर्ट सहेजने का स्थान", "changeFolder" to "फ़ोल्डर बदलें",
            "logout" to "लॉग आउट", "home" to "होम", "master" to "मास्टर", "report" to "रिपोर्ट",
            "labourRouting" to "श्रमिक नियुक्ति और रूटिंग", "labourRoutingSub" to "लाइव कर्मचारी तैनाती, सक्रिय कार्य और दर्ज घंटे देखें।",
            "confirmLogout" to "लॉग आउट की पुष्टि करें", "logoutQuestion" to "क्या आप सक्रिय पर्यवेक्षक सत्र से लॉग आउट करना चाहते हैं? स्थानीय डेटा सुरक्षित रहेगा।",
            "confirm" to "लॉग आउट", "cancel" to "रद्द करें"
        )
        AppLanguage.TELUGU -> mapOf(
            "terminal" to "లైవ్ లేబర్ మరియు మెట్రిక్స్ టెర్మినల్", "supervisor" to "సూపర్‌వైజర్",
            "account" to "ఖాతా మరియు ప్రాధాన్యతలు", "online" to "ఆన్‌లైన్", "email" to "ఇమెయిల్",
            "language" to "యాప్ భాష", "languageHint" to "యాప్ మరియు వాయిస్ భాష", "theme" to "థీమ్",
            "reports" to "రిపోర్ట్ సేవ్ స్థానం", "changeFolder" to "ఫోల్డర్ మార్చండి", "logout" to "లాగ్ అవుట్",
            "home" to "హోమ్", "master" to "మాస్టర్", "report" to "రిపోర్ట్",
            "labourRouting" to "కార్మిక కేటాయింపు మరియు రూటింగ్", "labourRoutingSub" to "లైవ్ సిబ్బంది కేటాయింపు, పనులు మరియు పని గంటలను ట్రాక్ చేయండి.",
            "confirmLogout" to "లాగ్ అవుట్ నిర్ధారించండి", "logoutQuestion" to "సక్రియ సూపర్‌వైజర్ సెషన్ నుండి లాగ్ అవుట్ చేయాలా? స్థానిక డేటా భద్రంగా ఉంటుంది.",
            "confirm" to "లాగ్ అవుట్", "cancel" to "రద్దు"
        )
        AppLanguage.KANNADA -> mapOf(
            "terminal" to "ಲೈವ್ ಕಾರ್ಮಿಕ ಮತ್ತು ಮೆಟ್ರಿಕ್ಸ್ ಟರ್ಮಿನಲ್", "supervisor" to "ಮೇಲ್ವಿಚಾರಕ",
            "account" to "ಖಾತೆ ಮತ್ತು ಆದ್ಯತೆಗಳು", "online" to "ಆನ್‌ಲೈನ್", "email" to "ಇಮೇಲ್",
            "language" to "ಅಪ್ಲಿಕೇಶನ್ ಭಾಷೆ", "languageHint" to "ಅಪ್ಲಿಕೇಶನ್ ಮತ್ತು ಧ್ವನಿ ಭಾಷೆ", "theme" to "ಥೀಮ್",
            "reports" to "ವರದಿ ಉಳಿಸುವ ಸ್ಥಳ", "changeFolder" to "ಫೋಲ್ಡರ್ ಬದಲಿಸಿ", "logout" to "ಲಾಗ್ ಔಟ್",
            "home" to "ಮುಖಪುಟ", "master" to "ಮಾಸ್ಟರ್", "report" to "ವರದಿ",
            "labourRouting" to "ಕಾರ್ಮಿಕ ನಿಯೋಜನೆ ಮತ್ತು ರೂಟಿಂಗ್", "labourRoutingSub" to "ನೇರ ಸಿಬ್ಬಂದಿ ನಿಯೋಜನೆ, ಕೆಲಸಗಳು ಮತ್ತು ಗಂಟೆಗಳನ್ನು ಟ್ರ್ಯಾಕ್ ಮಾಡಿ.",
            "confirmLogout" to "ಲಾಗ್ ಔಟ್ ದೃಢೀಕರಿಸಿ", "logoutQuestion" to "ಸಕ್ರಿಯ ಮೇಲ್ವಿಚಾರಕ ಸೆಷನ್‌ನಿಂದ ಲಾಗ್ ಔಟ್ ಮಾಡಬೇಕೇ? ಸ್ಥಳೀಯ ಡೇಟಾ ಸುರಕ್ಷಿತವಾಗಿರುತ್ತದೆ.",
            "confirm" to "ಲಾಗ್ ಔಟ್", "cancel" to "ರದ್ದು"
        )
        AppLanguage.MALAYALAM -> mapOf(
            "terminal" to "ലൈവ് ലേബർ ആൻഡ് മെട്രിക്സ് ടെർമിനൽ", "supervisor" to "സൂപ്പർവൈസർ",
            "account" to "അക്കൗണ്ടും മുൻഗണനകളും", "online" to "ഓൺലൈൻ", "email" to "ഇമെയിൽ",
            "language" to "ആപ്പ് ഭാഷ", "languageHint" to "ആപ്പിന്റെയും ശബ്ദത്തിന്റെയും ഭാഷ", "theme" to "തീം",
            "reports" to "റിപ്പോർട്ട് സേവ് സ്ഥലം", "changeFolder" to "ഫോൾഡർ മാറ്റുക", "logout" to "ലോഗ് ഔട്ട്",
            "home" to "ഹോം", "master" to "മാസ്റ്റർ", "report" to "റിപ്പോർട്ട്",
            "labourRouting" to "തൊഴിലാളി നിയമനവും റൂട്ടിംഗും", "labourRoutingSub" to "തത്സമയ തൊഴിലാളി വിന്യാസം, ജോലികൾ, ജോലി സമയം എന്നിവ നിരീക്ഷിക്കുക.",
            "confirmLogout" to "ലോഗ് ഔട്ട് സ്ഥിരീകരിക്കുക", "logoutQuestion" to "സജീവ സൂപ്പർവൈസർ സെഷനിൽ നിന്ന് ലോഗ് ഔട്ട് ചെയ്യണോ? പ്രാദേശിക ഡാറ്റ സുരക്ഷിതമായിരിക്കും.",
            "confirm" to "ലോഗ് ഔട്ട്", "cancel" to "റദ്ദാക്കുക"
        )
    }
    val english = mapOf(
        "terminal" to "Live Labour and Metrics Terminal", "supervisor" to "Supervisor",
        "account" to "Account & Preferences", "online" to "Online", "email" to "Email",
        "language" to "App Language", "languageHint" to "Language for app and speech", "theme" to "Theme",
        "reports" to "Report Save Location", "changeFolder" to "Change folder", "logout" to "Log Out",
        "home" to "Home", "master" to "Master", "report" to "Report",
        "labourRouting" to "Labour Assignments & Routing", "labourRoutingSub" to "Track live workforce deployment, active job assignments, and clocked hours.",
        "confirmLogout" to "Confirm Log Out", "logoutQuestion" to "Are you sure you want to log out of the active Supervisor session? All local data will remain saved.",
        "confirm" to "Log Out", "cancel" to "Cancel"
    )
    val recoveryTranslations = when (language) {
        AppLanguage.ENGLISH -> mapOf(
            "dataRecovery" to "Data Recovery", "recycleBin" to "Recycle Bin",
            "recycleHint" to "View and restore deleted records", "retention" to "Retention period",
            "restore" to "Restore", "emptyBin" to "No deleted records", "days" to "days", "done" to "Done",
            "backupPath" to "Backup path", "changeBackupPath" to "Change backup folder", "protectedPath" to "Protected database path"
        )
        AppLanguage.TAMIL -> mapOf(
            "dataRecovery" to "தரவு மீட்பு", "recycleBin" to "மறுசுழற்சி தொட்டி",
            "recycleHint" to "நீக்கப்பட்ட பதிவுகளைப் பார்த்து மீட்டெடுக்கவும்", "retention" to "தக்கவைப்பு காலம்",
            "restore" to "மீட்டெடு", "emptyBin" to "நீக்கப்பட்ட பதிவுகள் இல்லை", "days" to "நாட்கள்", "done" to "முடிந்தது",
            "backupPath" to "காப்புப் பாதை", "changeBackupPath" to "காப்பு கோப்புறையை மாற்று", "protectedPath" to "பாதுகாக்கப்பட்ட தரவுத்தள பாதை"
        )
        AppLanguage.HINDI -> mapOf(
            "dataRecovery" to "डेटा पुनर्प्राप्ति", "recycleBin" to "रीसायकल बिन",
            "recycleHint" to "हटाए गए रिकॉर्ड देखें और पुनर्स्थापित करें", "retention" to "संग्रह अवधि",
            "restore" to "पुनर्स्थापित करें", "emptyBin" to "कोई हटाया गया रिकॉर्ड नहीं", "days" to "दिन", "done" to "पूर्ण",
            "backupPath" to "बैकअप पथ", "changeBackupPath" to "बैकअप फ़ोल्डर बदलें", "protectedPath" to "सुरक्षित डेटाबेस पथ"
        )
        AppLanguage.TELUGU -> mapOf(
            "dataRecovery" to "డేటా పునరుద్ధరణ", "recycleBin" to "రీసైకిల్ బిన్",
            "recycleHint" to "తొలగించిన రికార్డులను చూసి పునరుద్ధరించండి", "retention" to "నిల్వ వ్యవధి",
            "restore" to "పునరుద్ధరించు", "emptyBin" to "తొలగించిన రికార్డులు లేవు", "days" to "రోజులు", "done" to "పూర్తయింది",
            "backupPath" to "బ్యాకప్ మార్గం", "changeBackupPath" to "బ్యాకప్ ఫోల్డర్ మార్చండి", "protectedPath" to "రక్షిత డేటాబేస్ మార్గం"
        )
        AppLanguage.KANNADA -> mapOf(
            "dataRecovery" to "ಡೇಟಾ ಮರುಪಡೆಯುವಿಕೆ", "recycleBin" to "ಮರುಬಳಕೆ ಬುಟ್ಟಿ",
            "recycleHint" to "ಅಳಿಸಿದ ದಾಖಲೆಗಳನ್ನು ನೋಡಿ ಮರುಸ್ಥಾಪಿಸಿ", "retention" to "ಉಳಿಸುವ ಅವಧಿ",
            "restore" to "ಮರುಸ್ಥಾಪಿಸಿ", "emptyBin" to "ಅಳಿಸಿದ ದಾಖಲೆಗಳಿಲ್ಲ", "days" to "ದಿನಗಳು", "done" to "ಮುಗಿದಿದೆ",
            "backupPath" to "ಬ್ಯಾಕಪ್ ಮಾರ್ಗ", "changeBackupPath" to "ಬ್ಯಾಕಪ್ ಫೋಲ್ಡರ್ ಬದಲಿಸಿ", "protectedPath" to "ರಕ್ಷಿತ ಡೇಟಾಬೇಸ್ ಮಾರ್ಗ"
        )
        AppLanguage.MALAYALAM -> mapOf(
            "dataRecovery" to "ഡാറ്റ വീണ്ടെടുക്കൽ", "recycleBin" to "റീസൈക്കിൾ ബിൻ",
            "recycleHint" to "ഇല്ലാതാക്കിയ രേഖകൾ കണ്ട് വീണ്ടെടുക്കുക", "retention" to "സൂക്ഷിക്കൽ കാലം",
            "restore" to "വീണ്ടെടുക്കുക", "emptyBin" to "ഇല്ലാതാക്കിയ രേഖകളില്ല", "days" to "ദിവസങ്ങൾ", "done" to "പൂർത്തിയായി",
            "backupPath" to "ബാക്കപ്പ് പാത", "changeBackupPath" to "ബാക്കപ്പ് ഫോൾഡർ മാറ്റുക", "protectedPath" to "സംരക്ഷിത ഡാറ്റാബേസ് പാത"
        )
    }
    return recoveryTranslations[key] ?: translations[key] ?: english[key] ?: key
}

private fun operationalText(language: AppLanguage, english: String): String {
    val tamil = mapOf(
        "Shop Floor Executive Metrics" to "பணித்தள நிர்வாக அளவீடுகள்", "All Departments Active" to "அனைத்து துறைகளும் செயலில் உள்ளன",
        "All Depts" to "அனைத்து துறைகள்", "Department" to "துறை", "Active Orders" to "செயலில் உள்ள ஆர்டர்கள்",
        "{count} order archived completed" to "{count} முடிக்கப்பட்ட ஆர்டர் காப்பகப்படுத்தப்பட்டது", "Cost Variance" to "செலவு மாறுபாடு",
        "Favorable (Under budget)" to "சாதகமானது (பட்ஜெட்டிற்குள்)", "Unfavorable (Over budget)" to "பாதகமானது (பட்ஜெட்டை மீறியது)",
        "Labour Utilization" to "தொழிலாளர் பயன்பாடு", "Planned limit: {hours} manhours" to "திட்டமிட்ட வரம்பு: {hours} மனிதநேரம்",
        "Labour Efficiency" to "தொழிலாளர் செயல்திறன்", "Overall Productivity Index" to "ஒட்டுமொத்த உற்பத்தித்திறன் குறியீடு",
        "Actual vs. Planned Cost by Sales Order" to "விற்பனை ஆர்டர் வாரியாக உண்மை மற்றும் திட்டமிட்ட செலவு", "Values in INR (₹)" to "இந்திய ரூபாயில் மதிப்புகள் (₹)",
        "Planned Cost" to "திட்டமிட்ட செலவு", "Actual Cost" to "உண்மையான செலவு", "Cost by Department" to "துறை வாரியான செலவு",
        "Labour Deployment" to "தொழிலாளர் பணியமர்த்தல்", "Current clocked hours · last 6 employees" to "தற்போதைய பதிவு நேரம் · கடைசி 6 பணியாளர்கள்",
        "Total" to "மொத்தம்", "No clocked hours yet" to "பதிவு செய்யப்பட்ட நேரம் இல்லை", "Manhours by Category" to "வகை வாரியான மனிதநேரம்",
        "Ranked by total consumed hours" to "பயன்படுத்திய மொத்த நேரத்தின் வரிசை", "No category hours yet" to "வகை நேரத் தரவு இல்லை",
        "All Departments" to "அனைத்து துறைகள்", "Search orders workers" to "விற்பனை ஆர்டர், பொருள் அல்லது பணியாளர் பெயரைத் தேடுக...",
        "Sales Orders Running" to "இயங்கும் விற்பனை ஆர்டர்கள்", "No matching orders" to "இந்தத் துறையில் பொருந்தும் விற்பனை ஆர்டர்கள் இல்லை.",
        "Authorized offline terminal" to "அங்கீகரிக்கப்பட்ட நிலைய முனையம் · உள்ளூர் சேமிப்பு செயலில் உள்ளது", "hrs" to "மணி"
    )
    val hindi = mapOf(
        "Shop Floor Executive Metrics" to "शॉप फ्लोर कार्यकारी मेट्रिक्स", "All Departments Active" to "सभी विभाग सक्रिय",
        "All Depts" to "सभी विभाग", "Department" to "विभाग", "Active Orders" to "सक्रिय ऑर्डर",
        "{count} order archived completed" to "{count} पूर्ण ऑर्डर संग्रहित", "Cost Variance" to "लागत अंतर",
        "Favorable (Under budget)" to "अनुकूल (बजट के भीतर)", "Unfavorable (Over budget)" to "प्रतिकूल (बजट से अधिक)",
        "Labour Utilization" to "श्रम उपयोग", "Planned limit: {hours} manhours" to "नियोजित सीमा: {hours} मानव-घंटे",
        "Labour Efficiency" to "श्रम दक्षता", "Overall Productivity Index" to "समग्र उत्पादकता सूचकांक",
        "Actual vs. Planned Cost by Sales Order" to "बिक्री ऑर्डर के अनुसार वास्तविक बनाम नियोजित लागत", "Values in INR (₹)" to "भारतीय रुपये में मान (₹)",
        "Planned Cost" to "नियोजित लागत", "Actual Cost" to "वास्तविक लागत", "Cost by Department" to "विभाग के अनुसार लागत",
        "Labour Deployment" to "श्रमिक तैनाती", "Current clocked hours · last 6 employees" to "वर्तमान दर्ज घंटे · अंतिम 6 कर्मचारी",
        "Total" to "कुल", "No clocked hours yet" to "अभी कोई दर्ज घंटे नहीं", "Manhours by Category" to "श्रेणी के अनुसार मानव-घंटे",
        "Ranked by total consumed hours" to "कुल उपयोग किए घंटों के अनुसार क्रम", "No category hours yet" to "श्रेणी घंटे उपलब्ध नहीं",
        "All Departments" to "सभी विभाग", "Search orders workers" to "बिक्री ऑर्डर, वस्तु या कर्मचारी का नाम खोजें...", "Sales Orders Running" to "चल रहे बिक्री ऑर्डर",
        "No matching orders" to "इस विभाग में कोई मेल खाता बिक्री ऑर्डर नहीं मिला।", "Authorized offline terminal" to "अधिकृत स्टेशन टर्मिनल · स्थानीय संग्रह सक्रिय", "hrs" to "घंटे"
    )
    val telugu = mapOf(
        "Shop Floor Executive Metrics" to "షాప్ ఫ్లోర్ కార్యనిర్వాహక ప్రమాణాలు", "All Departments Active" to "అన్ని విభాగాలు సక్రియంగా ఉన్నాయి", "All Depts" to "అన్ని విభాగాలు",
        "Department" to "విభాగం", "Active Orders" to "సక్రియ ఆర్డర్లు", "{count} order archived completed" to "{count} పూర్తయిన ఆర్డర్ భద్రపరచబడింది",
        "Cost Variance" to "ఖర్చు వ్యత్యాసం", "Favorable (Under budget)" to "అనుకూలం (బడ్జెట్‌లో)", "Unfavorable (Over budget)" to "ప్రతికూలం (బడ్జెట్‌ మించి)",
        "Labour Utilization" to "కార్మిక వినియోగం", "Planned limit: {hours} manhours" to "ప్రణాళిక పరిమితి: {hours} మానవ గంటలు", "Labour Efficiency" to "కార్మిక సామర్థ్యం",
        "Overall Productivity Index" to "మొత్తం ఉత్పాదకత సూచిక", "Actual vs. Planned Cost by Sales Order" to "సేల్స్ ఆర్డర్ వారీగా వాస్తవ మరియు ప్రణాళిక ఖర్చు",
        "Values in INR (₹)" to "భారత రూపాయల్లో విలువలు (₹)", "Planned Cost" to "ప్రణాళిక ఖర్చు", "Actual Cost" to "వాస్తవ ఖర్చు", "Cost by Department" to "విభాగం వారీ ఖర్చు",
        "Labour Deployment" to "కార్మిక నియామకం", "Current clocked hours · last 6 employees" to "ప్రస్తుత నమోదు గంటలు · చివరి 6 ఉద్యోగులు", "Total" to "మొత్తం",
        "No clocked hours yet" to "నమోదైన గంటలు లేవు", "Manhours by Category" to "వర్గం వారీ మానవ గంటలు", "Ranked by total consumed hours" to "వినియోగించిన మొత్తం గంటల క్రమం", "No category hours yet" to "వర్గ గంటలు లేవు",
        "All Departments" to "అన్ని విభాగాలు", "Search orders workers" to "సేల్స్ ఆర్డర్, వస్తువు లేదా ఉద్యోగి పేరును వెతకండి...", "Sales Orders Running" to "నడుస్తున్న సేల్స్ ఆర్డర్లు",
        "No matching orders" to "ఈ విభాగంలో సరిపోలే సేల్స్ ఆర్డర్లు లేవు.", "Authorized offline terminal" to "అధీకృత స్టేషన్ టెర్మినల్ · స్థానిక నిల్వ సక్రియం", "hrs" to "గంటలు"
    )
    val kannada = mapOf(
        "Shop Floor Executive Metrics" to "ಶಾಪ್ ಫ್ಲೋರ್ ಕಾರ್ಯನಿರ್ವಾಹಕ ಮಾಪಕಗಳು", "All Departments Active" to "ಎಲ್ಲಾ ವಿಭಾಗಗಳು ಸಕ್ರಿಯ", "All Depts" to "ಎಲ್ಲಾ ವಿಭಾಗಗಳು", "Department" to "ವಿಭಾಗ",
        "Active Orders" to "ಸಕ್ರಿಯ ಆರ್ಡರ್‌ಗಳು", "{count} order archived completed" to "{count} ಪೂರ್ಣಗೊಂಡ ಆರ್ಡರ್ ಸಂಗ್ರಹಿಸಲಾಗಿದೆ", "Cost Variance" to "ವೆಚ್ಚ ವ್ಯತ್ಯಾಸ",
        "Favorable (Under budget)" to "ಅನುಕೂಲಕರ (ಬಜೆಟ್ ಒಳಗೆ)", "Unfavorable (Over budget)" to "ಪ್ರತಿಕೂಲ (ಬಜೆಟ್ ಮೀರಿದೆ)", "Labour Utilization" to "ಕಾರ್ಮಿಕ ಬಳಕೆ",
        "Planned limit: {hours} manhours" to "ಯೋಜಿತ ಮಿತಿ: {hours} ಮಾನವ ಗಂಟೆಗಳು", "Labour Efficiency" to "ಕಾರ್ಮಿಕ ದಕ್ಷತೆ", "Overall Productivity Index" to "ಒಟ್ಟಾರೆ ಉತ್ಪಾದಕತೆ ಸೂಚ್ಯಂಕ",
        "Actual vs. Planned Cost by Sales Order" to "ಮಾರಾಟ ಆರ್ಡರ್ ಪ್ರಕಾರ ನೈಜ ಮತ್ತು ಯೋಜಿತ ವೆಚ್ಚ", "Values in INR (₹)" to "ಭಾರತೀಯ ರೂಪಾಯಿಗಳಲ್ಲಿ ಮೌಲ್ಯಗಳು (₹)", "Planned Cost" to "ಯೋಜಿತ ವೆಚ್ಚ",
        "Actual Cost" to "ನೈಜ ವೆಚ್ಚ", "Cost by Department" to "ವಿಭಾಗವಾರು ವೆಚ್ಚ", "Labour Deployment" to "ಕಾರ್ಮಿಕ ನಿಯೋಜನೆ", "Current clocked hours · last 6 employees" to "ಪ್ರಸ್ತುತ ದಾಖಲಾದ ಗಂಟೆಗಳು · ಕೊನೆಯ 6 ಉದ್ಯೋಗಿಗಳು",
        "Total" to "ಒಟ್ಟು", "No clocked hours yet" to "ದಾಖಲಾದ ಗಂಟೆಗಳಿಲ್ಲ", "Manhours by Category" to "ವರ್ಗವಾರು ಮಾನವ ಗಂಟೆಗಳು", "Ranked by total consumed hours" to "ಬಳಸಿದ ಒಟ್ಟು ಗಂಟೆಗಳ ಕ್ರಮ", "No category hours yet" to "ವರ್ಗದ ಗಂಟೆಗಳಿಲ್ಲ",
        "All Departments" to "ಎಲ್ಲಾ ವಿಭಾಗಗಳು", "Search orders workers" to "ಮಾರಾಟ ಆರ್ಡರ್, ವಸ್ತು ಅಥವಾ ಉದ್ಯೋಗಿಯ ಹೆಸರನ್ನು ಹುಡುಕಿ...", "Sales Orders Running" to "ಚಾಲನೆಯಲ್ಲಿರುವ ಮಾರಾಟ ಆರ್ಡರ್‌ಗಳು",
        "No matching orders" to "ಈ ವಿಭಾಗದಲ್ಲಿ ಹೊಂದುವ ಮಾರಾಟ ಆರ್ಡರ್‌ಗಳಿಲ್ಲ.", "Authorized offline terminal" to "ಅಧಿಕೃತ ಸ್ಟೇಷನ್ ಟರ್ಮಿನಲ್ · ಸ್ಥಳೀಯ ಸಂಗ್ರಹ ಸಕ್ರಿಯ", "hrs" to "ಗಂಟೆಗಳು"
    )
    val malayalam = mapOf(
        "Shop Floor Executive Metrics" to "ഷോപ്പ് ഫ്ലോർ എക്സിക്യൂട്ടീവ് മെട്രിക്സ്", "All Departments Active" to "എല്ലാ വകുപ്പുകളും സജീവമാണ്", "All Depts" to "എല്ലാ വകുപ്പുകളും", "Department" to "വകുപ്പ്",
        "Active Orders" to "സജീവ ഓർഡറുകൾ", "{count} order archived completed" to "{count} പൂർത്തിയായ ഓർഡർ ശേഖരിച്ചു", "Cost Variance" to "ചെലവ് വ്യത്യാസം",
        "Favorable (Under budget)" to "അനുകൂലം (ബജറ്റിനുള്ളിൽ)", "Unfavorable (Over budget)" to "പ്രതികൂലം (ബജറ്റ് കവിഞ്ഞു)", "Labour Utilization" to "തൊഴിലാളി വിനിയോഗം",
        "Planned limit: {hours} manhours" to "ആസൂത്രിത പരിധി: {hours} മനുഷ്യ മണിക്കൂർ", "Labour Efficiency" to "തൊഴിൽ കാര്യക്ഷമത", "Overall Productivity Index" to "മൊത്തം ഉൽപ്പാദനക്ഷമത സൂചിക",
        "Actual vs. Planned Cost by Sales Order" to "സെയിൽസ് ഓർഡർ അനുസരിച്ച് യഥാർത്ഥവും ആസൂത്രിതവുമായ ചെലവ്", "Values in INR (₹)" to "ഇന്ത്യൻ രൂപയിലെ മൂല്യങ്ങൾ (₹)", "Planned Cost" to "ആസൂത്രിത ചെലവ്",
        "Actual Cost" to "യഥാർത്ഥ ചെലവ്", "Cost by Department" to "വകുപ്പ് തിരിച്ചുള്ള ചെലവ്", "Labour Deployment" to "തൊഴിലാളി വിന്യാസം", "Current clocked hours · last 6 employees" to "നിലവിലെ രേഖപ്പെടുത്തിയ സമയം · അവസാന 6 ജീവനക്കാർ",
        "Total" to "ആകെ", "No clocked hours yet" to "രേഖപ്പെടുത്തിയ സമയം ഇല്ല", "Manhours by Category" to "വിഭാഗം തിരിച്ചുള്ള മനുഷ്യ മണിക്കൂർ", "Ranked by total consumed hours" to "ഉപയോഗിച്ച മൊത്തം സമയക്രമം", "No category hours yet" to "വിഭാഗ സമയമില്ല",
        "All Departments" to "എല്ലാ വകുപ്പുകളും", "Search orders workers" to "സെയിൽസ് ഓർഡർ, ഇനം അല്ലെങ്കിൽ ജീവനക്കാരന്റെ പേര് തിരയുക...", "Sales Orders Running" to "പ്രവർത്തിക്കുന്ന സെയിൽസ് ഓർഡറുകൾ",
        "No matching orders" to "ഈ വകുപ്പിൽ പൊരുത്തപ്പെടുന്ന സെയിൽസ് ഓർഡറുകളില്ല.", "Authorized offline terminal" to "അംഗീകൃത സ്റ്റേഷൻ ടെർമിനൽ · പ്രാദേശിക സംഭരണം സജീവം", "hrs" to "മണിക്കൂർ"
    )
    return when (language) {
        AppLanguage.ENGLISH -> english
        AppLanguage.TAMIL -> tamil[english]
        AppLanguage.HINDI -> hindi[english]
        AppLanguage.TELUGU -> telugu[english]
        AppLanguage.KANNADA -> kannada[english]
        AppLanguage.MALAYALAM -> malayalam[english]
    } ?: english
}

private fun currentOperationalLanguage(): AppLanguage = activeOperationalLanguage.value

private fun glassBackground(style: GlassThemeStyle): Brush = when (style) {
    GlassThemeStyle.ROYAL_GLASS -> Brush.verticalGradient(
        colors = listOf(Color(0xFFF7F2FF), Color(0xFFEDE9FE), Color(0xFFF8FAFF))
    )
    GlassThemeStyle.AURORA_GLASS -> Brush.verticalGradient(
        colors = listOf(Color(0xFFEAFBFA), Color(0xFFEAF1FF), Color(0xFFF5F0FF))
    )
}

private fun themeSurface(style: GlassThemeStyle): Color = when (style) {
    GlassThemeStyle.ROYAL_GLASS -> Color.White.copy(alpha = 0.78f)
    GlassThemeStyle.AURORA_GLASS -> Color.White.copy(alpha = 0.72f)
}

private fun themeCardBorder(style: GlassThemeStyle): Color = when (style) {
    GlassThemeStyle.ROYAL_GLASS, GlassThemeStyle.AURORA_GLASS -> Color(0xFFD8B4FE)
}

private fun themeAccent(style: GlassThemeStyle): Color = when (style) {
    GlassThemeStyle.ROYAL_GLASS -> Color(0xFF6D28D9)
    GlassThemeStyle.AURORA_GLASS -> Color(0xFF0E7490)
}

@Composable
private fun ProfileThemeToggle(
    title: String,
    subtitle: String,
    swatchColors: List<Color>,
    darkPanel: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.linearGradient(
                    colors = if (darkPanel) {
                        listOf(Color(0xFF2E1065), Color(0xFF581C87))
                    } else {
                        listOf(Color.White.copy(alpha = 0.92f), Color(0xFFF3E8FF))
                    }
                )
            )
            .border(
                1.dp,
                if (darkPanel) Color(0xFFA855F7).copy(alpha = 0.65f)
                else Color(0xFFD8B4FE),
                RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .heightIn(min = 64.dp)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Brush.linearGradient(swatchColors), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Palette,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(21.dp)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                "ACTIVE THEME",
                color = if (darkPanel) Color(0xFFD8B4FE) else Color(0xFF7E22CE),
                fontSize = 9.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.8.sp
            )
            Text(
                title,
                color = if (darkPanel) Color.White else Color(0xFF2E1065),
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                subtitle,
                color = if (darkPanel) Color(0xFFE9D5FF) else Color(0xFF6B4A7D),
                fontSize = 10.sp
            )
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(
                    if (darkPanel) Color(0xFFF59E0B)
                    else Color(0xFF6D28D9)
                )
                .heightIn(min = 40.dp)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "Switch to next theme",
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "SWITCH",
                    color = Color.White,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Composable
private fun ProfileSettingRow(
    icon: ImageVector,
    iconColor: Color,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 68.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.88f))
            .border(1.dp, Color(0xFFE3C8FA), RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(42.dp).clip(RoundedCornerShape(12.dp))
                .background(iconColor.copy(alpha = 0.13f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(23.dp))
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(title, color = Color(0xFF2E1065), fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(Modifier.height(2.dp))
            Text(subtitle, color = Color(0xFF67557A), fontSize = 11.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
        Icon(
            Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            tint = Color(0xFF6D28D9),
            modifier = Modifier.size(20.dp).graphicsLayer { rotationZ = 180f }
        )
    }
}

@Composable
private fun ProfilePage(
    modifier: Modifier = Modifier,
    profileName: String,
    userEmail: String,
    managementAlertEmail: String,
    appLanguage: AppLanguage,
    glassThemeStyle: GlassThemeStyle,
    recoverableCount: Int,
    recycleRetentionDays: Int,
    reportLocationLabel: String,
    onBack: () -> Unit,
    onLanguageChange: (AppLanguage) -> Unit,
    onThemeChange: (GlassThemeStyle) -> Unit,
    onOpenRecycleBin: () -> Unit,
    onChangeReportFolder: () -> Unit,
    onLogout: () -> Unit
) {
    val primary = Color(0xFF2E1065)
    val secondary = Color(0xFF67557A)
    var languageExpanded by remember { mutableStateOf(false) }
    val profileScrollState = rememberScrollState()
    LaunchedEffect(Unit) { profileScrollState.scrollTo(0) }
    Column(
        modifier = modifier.fillMaxSize().background(glassBackground(glassThemeStyle))
            .verticalScroll(profileScrollState).padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack, modifier = Modifier.size(48.dp)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = primary)
            }
            Spacer(Modifier.width(4.dp))
            Column {
                Text("Profile", color = primary, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp)
                Text(uiText(appLanguage, "account"), color = secondary, fontSize = 12.sp)
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Row(
                Modifier.background(Brush.linearGradient(listOf(Color(0xFF4C1D95), Color(0xFF7C3AED), Color(0xFF9333EA))))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier.size(56.dp).clip(CircleShape)
                        .background(Brush.linearGradient(listOf(Color(0xFFA855F7), Color(0xFFF59E0B))))
                        .border(2.dp, Color.White.copy(alpha = .8f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(profileName.take(1).uppercase(), color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp)
                }
                Spacer(Modifier.width(14.dp))
                Column(Modifier.weight(1f)) {
                    Text(profileName, color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text(uiText(appLanguage, "supervisor"), color = Color(0xFFE9D5FF), fontSize = 12.sp)
                    Spacer(Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(7.dp).clip(CircleShape).background(Color(0xFF22C55E)))
                        Spacer(Modifier.width(5.dp))
                        Text(uiText(appLanguage, "online"), color = Color.White, fontSize = 11.sp)
                    }
                }
            }
        }

        Surface(
            color = themeSurface(glassThemeStyle), shape = RoundedCornerShape(18.dp),
            border = BorderStroke(1.dp, themeCardBorder(glassThemeStyle))
        ) {
            Column(Modifier.padding(horizontal = 16.dp, vertical = 12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.Person, contentDescription = null, tint = Color(0xFF6D28D9), modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text(uiText(appLanguage, "email").uppercase(), color = Color(0xFF6D28D9), fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        Text(userEmail, color = Color(0xFF2E1065), fontSize = 13.sp, fontWeight = FontWeight.Medium)
                    }
                }
                HorizontalDivider(color = Color(0xFFE3C8FA))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Warning, contentDescription = null, tint = Color(0xFFB45309), modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Column(Modifier.weight(1f)) {
                        Text("MANAGEMENT ALERT EMAIL", color = Color(0xFFB45309), fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        Text(managementAlertEmail.ifBlank { "Not configured" }, color = Color(0xFF2E1065), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text("Protected by management · cannot be edited in the app", color = Color(0xFF67557A), fontSize = 10.sp)
                    }
                    Icon(Icons.Filled.Key, contentDescription = "Management locked", tint = Color(0xFF6D28D9), modifier = Modifier.size(18.dp))
                }
            }
        }

        Text(uiText(appLanguage, "account").uppercase(), color = Color(0xFF6D28D9), fontSize = 10.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = .8.sp)
        Box {
            ProfileSettingRow(
                icon = Icons.Filled.Language,
                iconColor = Color(0xFF16815D),
                title = uiText(appLanguage, "language"),
                subtitle = "${appLanguage.nativeName} · ${uiText(appLanguage, "languageHint")}",
                onClick = { languageExpanded = true }
            )
            DropdownMenu(
                expanded = languageExpanded,
                onDismissRequest = { languageExpanded = false },
                modifier = Modifier.fillMaxWidth(.88f).background(Color.White)
            ) {
                AppLanguage.entries.forEach { language ->
                    DropdownMenuItem(
                        text = {
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Column {
                                    Text(language.nativeName, color = Color(0xFF2E1065), fontWeight = FontWeight.Bold)
                                    Text(language.tag.uppercase(), color = Color(0xFF67557A), fontSize = 10.sp)
                                }
                                if (language == appLanguage) Icon(Icons.Filled.CheckCircle, null, tint = Color(0xFF16815D))
                            }
                        },
                        onClick = { onLanguageChange(language); languageExpanded = false }
                    )
                }
            }
        }

        Text("THEME", color = themeAccent(glassThemeStyle), fontSize = 10.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = .8.sp)
        ProfileThemeToggle(
            title = if (glassThemeStyle == GlassThemeStyle.ROYAL_GLASS) "Royal Glass" else "Aurora Glass",
            subtitle = if (glassThemeStyle == GlassThemeStyle.ROYAL_GLASS) "Classic violet workspace" else "Cool teal workspace",
            swatchColors = if (glassThemeStyle == GlassThemeStyle.ROYAL_GLASS) listOf(Color(0xFF6D28D9), Color(0xFFA855F7)) else listOf(Color(0xFF0E7490), Color(0xFF22C55E)),
            darkPanel = false,
            onClick = {
                onThemeChange(if (glassThemeStyle == GlassThemeStyle.ROYAL_GLASS) GlassThemeStyle.AURORA_GLASS else GlassThemeStyle.ROYAL_GLASS)
            }
        )

        Text(uiText(appLanguage, "dataRecovery").uppercase(), color = Color(0xFF6D28D9), fontSize = 10.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = .8.sp)
        ProfileSettingRow(
            icon = Icons.Filled.History,
            iconColor = Color(0xFFB45309),
            title = uiText(appLanguage, "recycleBin"),
            subtitle = "$recoverableCount recoverable · $recycleRetentionDays ${uiText(appLanguage, "days")}",
            onClick = onOpenRecycleBin
        )

        Text(uiText(appLanguage, "reports").uppercase(), color = Color(0xFF6D28D9), fontSize = 10.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = .8.sp)
        ProfileSettingRow(
            icon = Icons.Filled.Folder,
            iconColor = Color(0xFF6D28D9),
            title = uiText(appLanguage, "changeFolder"),
            subtitle = reportLocationLabel,
            onClick = onChangeReportFolder
        )

        OutlinedButton(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFB91C1C)),
            border = BorderStroke(1.dp, Color(0xFFFCA5A5))
        ) {
            Icon(Icons.Filled.ExitToApp, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text(uiText(appLanguage, "logout"), fontWeight = FontWeight.ExtraBold)
        }
        Spacer(Modifier.height(12.dp))
    }
}

private fun canonicalizeVoiceCommand(text: String): String {
    var normalized = text.lowercase(Locale.getDefault())
    val replacements = linkedMapOf(
        // Spanish / Portuguese / French / German
        "orden de venta" to "sales order", "pedido de venda" to "sales order",
        "commande client" to "sales order", "verkaufsauftrag" to "sales order",
        "empleado" to "employee", "funcionário" to "employee", "employé" to "employee", "mitarbeiter" to "employee",
        "departamento" to "department", "département" to "department", "abteilung" to "department",
        "categoría" to "category", "categoria" to "category", "catégorie" to "category", "kategorie" to "category",
        "temporizador" to "timer", "minuteur" to "timer", "zeitgeber" to "timer",
        "iniciar" to "start", "comenzar" to "start", "iniciar" to "start", "démarrer" to "start", "starten" to "start",
        "detener" to "stop", "parar" to "stop", "arrêter" to "stop", "stoppen" to "stop",
        "crear" to "create", "criar" to "create", "créer" to "create", "erstellen" to "create",
        "nombre" to "name", "nome" to "name", "nom" to "name", "name" to "name",
        "cantidad" to "quantity", "quantidade" to "quantity", "quantité" to "quantity", "menge" to "quantity",
        "horas" to "hours", "heures" to "hours", "stunden" to "hours",
        "presupuesto" to "budget", "orçamento" to "budget", "budget" to "budget",
        "tarifa" to "rate", "taxa" to "rate", "taux" to "rate", "satz" to "rate",
        // Hindi
        "बिक्री आदेश" to "sales order", "कर्मचारी" to "employee", "विभाग" to "department", "श्रेणी" to "category",
        "टाइमर" to "timer", "शुरू" to "start", "प्रारंभ" to "start", "बंद" to "stop", "रोक" to "stop",
        "बनाओ" to "create", "बनाएं" to "create", "नाम" to "name", "मात्रा" to "quantity",
        "घंटे" to "hours", "बजट" to "budget", "दर" to "rate",
        // Tamil
        "விற்பனை ஆணை" to "sales order", "ஊழியர்" to "employee", "துறை" to "department", "வகை" to "category",
        "டைமர்" to "timer", "தொடங்கு" to "start", "நிறுத்து" to "stop", "உருவாக்கு" to "create",
        "பெயர்" to "name", "அளவு" to "quantity", "மணிநேரம்" to "hours", "பட்ஜெட்" to "budget", "விகிதம்" to "rate",
        // Telugu
        "అమ్మకపు ఆర్డర్" to "sales order", "ఉద్యోగి" to "employee", "విభాగం" to "department", "వర్గం" to "category",
        "టైమర్" to "timer", "ప్రారంభించు" to "start", "ఆపు" to "stop", "సృష్టించు" to "create",
        "పేరు" to "name", "పరిమాణం" to "quantity", "గంటలు" to "hours", "బడ్జెట్" to "budget", "రేటు" to "rate",
        // Arabic
        "أمر مبيعات" to "sales order", "موظف" to "employee", "قسم" to "department", "فئة" to "category",
        "مؤقت" to "timer", "ابدأ" to "start", "أوقف" to "stop", "إنشاء" to "create",
        "اسم" to "name", "كمية" to "quantity", "ساعات" to "hours", "ميزانية" to "budget", "معدل" to "rate"
    )
    replacements.forEach { (source, target) -> normalized = normalized.replace(source, target) }
    return normalized.replace(Regex("\\s+"), " ").trim()
}

private fun selectBestSpeechResult(
    candidates: List<String>,
    salesOrders: List<SalesOrder>,
    employees: List<EmployeeActivity>
): String? {
    if (candidates.isEmpty()) return null
    val commandTerms = listOf(
        "start", "stop", "create", "edit", "assign", "move", "shift", "report",
        "employee", "department", "category", "sales order", "timer", "hours", "budget"
    )
    return candidates
        .asSequence()
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .maxByOrNull { candidate ->
            val normalized = resolveSpokenEntityIds(candidate, salesOrders, employees)
            commandTerms.count { normalized.contains(it) } * 3 +
                salesOrders.count { normalized.contains(it.id.lowercase(Locale.ROOT)) } * 5 +
                employees.count {
                    normalized.contains(it.empId.lowercase(Locale.ROOT)) ||
                        normalized.contains(it.name.lowercase(Locale.ROOT))
                } * 5
        }
}

private fun spokenIdVariants(id: String, entity: String): List<String> {
    val match = Regex("([A-Za-z]+)[-_ ]*(\\d+)").matchEntire(id.trim()) ?: return listOf(id)
    val prefix = match.groupValues[1].lowercase(Locale.ROOT)
    val digits = match.groupValues[2]
    val digitWords = mapOf(
        '0' to "zero", '1' to "one", '2' to "two", '3' to "three", '4' to "four",
        '5' to "five", '6' to "six", '7' to "seven", '8' to "eight", '9' to "nine"
    )
    val spokenDigits = digits.mapNotNull(digitWords::get).joinToString(" ")
    val spacedDigits = digits.toCharArray().joinToString(" ")
    val spacedPrefix = prefix.toCharArray().joinToString(" ")
    val prefixes = when (entity) {
        "sales order" -> listOf(prefix, spacedPrefix, "so", "s o", "sales order", "sale order")
        else -> listOf(prefix, spacedPrefix, "emp", "e m p", "employee", "employee id")
    }
    return buildList {
        add(id.lowercase(Locale.ROOT))
        prefixes.distinct().forEach { spokenPrefix ->
            add("$spokenPrefix $digits")
            add("$spokenPrefix $spacedDigits")
            add("$spokenPrefix $spokenDigits")
        }
    }.distinct().sortedByDescending(String::length)
}

private fun resolveSpokenEntityIds(
    text: String,
    salesOrders: List<SalesOrder>,
    employees: List<EmployeeActivity>
): String {
    var resolved = canonicalizeVoiceCommand(text)
    val digitTokens = mapOf(
        "zero" to "0", "oh" to "0", "one" to "1", "two" to "2", "three" to "3",
        "four" to "4", "five" to "5", "six" to "6", "seven" to "7", "eight" to "8", "nine" to "9"
    )
    val spokenIdPattern = Regex(
        "\\b(sales order|sale order|s o|so|employee id|employee|e m p|emp)\\s+(?:id\\s+)?" +
            "((?:(?:zero|oh|one|two|three|four|five|six|seven|eight|nine|[0-9])\\s*){2,})\\b",
        RegexOption.IGNORE_CASE
    )
    resolved = spokenIdPattern.replace(resolved) { match ->
        val prefixText = match.groupValues[1].lowercase(Locale.ROOT)
        val digits = Regex("zero|oh|one|two|three|four|five|six|seven|eight|nine|[0-9]", RegexOption.IGNORE_CASE)
            .findAll(match.groupValues[2])
            .joinToString("") { token -> digitTokens[token.value.lowercase(Locale.ROOT)] ?: token.value }
        val prefix = if (prefixText.contains("emp") || prefixText.contains("employee") || prefixText == "e m p") "EMP" else "SO"
        "$prefix-$digits"
    }

    salesOrders.forEach { order ->
        spokenIdVariants(order.id, "sales order").forEach { variant ->
            resolved = resolved.replace(
                Regex("(?<![a-z0-9])${Regex.escape(variant)}(?![a-z0-9])", RegexOption.IGNORE_CASE),
                order.id
            )
        }
    }
    employees.forEach { employee ->
        spokenIdVariants(employee.empId, "employee").forEach { variant ->
            resolved = resolved.replace(
                Regex("(?<![a-z0-9])${Regex.escape(variant)}(?![a-z0-9])", RegexOption.IGNORE_CASE),
                employee.empId
            )
        }
    }

    val hints = buildList {
        employees.filter { employee ->
            resolved.contains(employee.name, ignoreCase = true) &&
                !resolved.contains(employee.empId, ignoreCase = true)
        }.forEach { add("employee ${it.name} has exact ID ${it.empId}") }

        salesOrders.filter { order ->
            resolved.contains(order.item, ignoreCase = true) &&
                !resolved.contains(order.id, ignoreCase = true)
        }.forEach { add("sales order ${it.item} has exact ID ${it.id}") }
    }
    return if (hints.isEmpty()) resolved else "$resolved. Entity matches: ${hints.joinToString("; ")}"
}

private fun buildAssistantLiveData(
    salesOrders: List<SalesOrder>,
    employees: List<EmployeeActivity>,
    departments: List<Department>,
    categories: List<LabourCategory>,
    assignments: List<LabourAssignment>
): String = buildString {
    appendLine("CURRENT_LOCAL_TIME=${java.text.SimpleDateFormat("hh:mm a, EEEE, dd MMM yyyy", Locale.getDefault()).format(java.util.Date())}")
    append("DEPTS=")
    appendLine(departments.joinToString(";") { "${it.code},${it.name}" })
    append("CATS=")
    appendLine(categories.joinToString(";") { "${it.code},${it.name},${it.hourlyRate}" })
    append("ORDERS=")
    appendLine(salesOrders.joinToString(";") {
        val assigned = employees.filter { employee -> employee.task.equals(it.id, true) }
        val actualHours = assigned.sumOf { employee -> employee.hoursClocked }
        val actualCost = assigned.sumOf { employee -> employee.hoursClocked * employee.hourlyRate }
        "${it.id},${it.item},${it.department},${it.status},target=${it.targetQty},done=${it.completedQty},plannedHrs=${it.plannedManhours},actualHrs=$actualHours,budget=${it.plannedBudget},actualCost=$actualCost,hoursExceeded=${it.plannedManhours > 0 && actualHours > it.plannedManhours},costExceeded=${it.plannedBudget > 0 && actualCost > it.plannedBudget},start=${it.startDate},end=${it.endDate},timer=${it.timerSeconds}"
    })
    append("EMPS=")
    appendLine(employees.joinToString(";") {
        "${it.empId},${it.name},${it.department},${it.category},${it.task},actualHrs=${it.hoursClocked},rate=${it.hourlyRate},skill=${it.skillLevel},status=${it.status}"
    })
    append("ASSIGNS=")
    append(assignments.joinToString(";") {
        "${it.employeeId},${it.salesOrderId},plannedHrs=${it.plannedHours},start=${it.startDate},end=${it.endDate},status=${it.status}"
    })
}

private data class VoiceSalesOrderDraft(
    val code: String,
    val customer: String,
    val description: String = "",
    val plannedHours: String = "",
    val budget: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val status: String = "Not Started"
)

@Composable
private fun FloatingShopfloorBot(
    viewModel: DashboardViewModel,
    salesOrders: List<SalesOrder>,
    employees: List<EmployeeActivity>,
    departments: List<Department>,
    categories: List<LabourCategory>,
    onOpenSupervisor: () -> Unit
) {
    // Assistant-specific aliases of the app's royal purple and gold palette.
    val botBackground = Color(0xFF210B3D)
    val botSurface = Color(0xFF2E1065)
    val botSurfaceRaised = Color(0xFF3B176B)
    val botSurfaceSoft = Color(0xFF4C1D7D)
    val botBorder = Color(0xFFA855F7)
    val botAccent = Color(0xFFF59E0B)
    val botText = Color(0xFFFFFBEB)
    val botTextMuted = Color(0xFFE9D5FF)
    val botFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = botAccent,
        unfocusedBorderColor = botBorder.copy(alpha = 0.72f),
        focusedLabelColor = botAccent,
        unfocusedLabelColor = botTextMuted,
        focusedTextColor = botText,
        unfocusedTextColor = botText,
        cursorColor = botAccent,
        focusedContainerColor = botBackground.copy(alpha = 0.76f),
        unfocusedContainerColor = botBackground.copy(alpha = 0.58f),
        focusedPlaceholderColor = botTextMuted.copy(alpha = 0.72f),
        unfocusedPlaceholderColor = botTextMuted.copy(alpha = 0.62f)
    )
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val keyPreferences = remember { context.getSharedPreferences("shopfloor_assistant_keys", android.content.Context.MODE_PRIVATE) }
    var isOpen by rememberSaveable { mutableStateOf(false) }
    var showApiSettings by rememberSaveable { mutableStateOf(false) }
    var sarvamApiKey by remember { mutableStateOf(keyPreferences.getString("sarvam", "").orEmpty()) }
    var geminiApiKey by remember { mutableStateOf(keyPreferences.getString("gemini", "").orEmpty()) }
    var isRecording by remember { mutableStateOf(false) }
    var isThinking by remember { mutableStateOf(false) }
    var recorder by remember { mutableStateOf<MediaRecorder?>(null) }
    var recordingFile by remember { mutableStateOf<File?>(null) }
    val assignments by viewModel.assignments.collectAsState()
    var assignmentDraft by remember { mutableStateOf<LabourAssignment?>(null) }
    var salesOrderDraft by remember { mutableStateOf<VoiceSalesOrderDraft?>(null) }
    var pendingAction by remember { mutableStateOf<AssistantInterpretation?>(null) }
    var offsetX by rememberSaveable { mutableStateOf(0f) }
    var offsetY by rememberSaveable { mutableStateOf(0f) }
    var input by remember { mutableStateOf("") }
    var lastControlledOrderId by rememberSaveable { mutableStateOf("") }
    val messages = remember {
        mutableStateListOf(
            "Hi! I'm the ShopFloor Assistant. Ask about employees, sales orders, reports, or app navigation."
        )
    }

    fun executeMessage(message: String, showUserMessage: Boolean = true) {
        val question = message.trim()
        if (question.isEmpty()) return
        if (showUserMessage) messages.add("You: $question")
        val normalized = canonicalizeVoiceCommand(question)
        fun field(label: String): String? = Regex(
            "(?:$label)\\s+(.+?)(?=\\s+(?:item|department|quantity|target|hours|budget|name|category|rate)\\s+|$)",
            RegexOption.IGNORE_CASE
        ).find(normalized)?.groupValues?.getOrNull(1)?.trim()
        val employeeId = Regex("(?:employee|emp)(?:\\s+id)?\\s+([a-z0-9-]+)", RegexOption.IGNORE_CASE)
            .find(normalized)?.groupValues?.getOrNull(1)?.uppercase()
        val orderId = Regex("(?:sales\\s*order|sale\\s*order|so)(?:\\s+id)?\\s+([a-z0-9-]+)", RegexOption.IGNORE_CASE)
            .find(normalized)?.groupValues?.getOrNull(1)?.uppercase()

        val mentionedEmployee = employees
            .sortedByDescending { it.name.length }
            .firstOrNull { normalized.contains(it.name.lowercase()) || normalized.contains(it.empId.lowercase()) }
        val mentionedOrder = salesOrders
            .sortedByDescending { it.item.length }
            .firstOrNull {
                normalized.contains(it.id.lowercase()) ||
                    (it.item.isNotBlank() && normalized.contains(it.item.lowercase()))
            }

        val answer = when {
            (normalized.contains("current time") || normalized.contains("what time") || normalized.contains("time now") || normalized == "time") ->
                "The current local time is ${java.text.SimpleDateFormat("hh:mm a", Locale.getDefault()).format(java.util.Date())}."
            (normalized.contains("today date") || normalized.contains("what date") || normalized.contains("today's date") || normalized == "date" || normalized == "day") ->
                "Today is ${java.text.SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault()).format(java.util.Date())}."
            (normalized.contains("start work") || normalized.contains("clock in")) -> {
                if (orderId == null) "Tell me which sales order to start."
                else {
                    val requested = mentionedEmployee?.let { listOf(it.empId) }.orEmpty()
                    if (viewModel.startVoiceWork(orderId, requested)) {
                        lastControlledOrderId = orderId
                        onOpenSupervisor()
                        "Opened Supervisor and started live work on $orderId${mentionedEmployee?.let { " for ${it.name}" }.orEmpty()}."
                    } else "I couldn't find that order or any assigned employee."
                }
            }
            normalized.contains("stop work") || normalized.contains("clock out") -> {
                val targetOrder = orderId ?: lastControlledOrderId.ifBlank { null }
                if (targetOrder != null && viewModel.stopVoiceTimer(targetOrder)) {
                    onOpenSupervisor()
                    "Stopped work on $targetOrder and saved the actual employee hours."
                } else "Tell me which running sales order to stop."
            }
            (normalized.contains("assign") || normalized.contains("planned hours")) && orderId != null -> {
                val employee = mentionedEmployee
                if (employee == null) "I couldn't match that employee exactly. Use their full name or employee ID."
                else {
                    val existing = assignments.firstOrNull {
                        it.employeeId.equals(employee.empId, true) && it.salesOrderId.equals(orderId, true)
                    }
                    val requestedHours = Regex("(?:to|hours?)\\s+(\\d+(?:\\.\\d+)?)", RegexOption.IGNORE_CASE)
                        .find(normalized)?.groupValues?.getOrNull(1)?.toDoubleOrNull()
                    assignmentDraft = (existing ?: LabourAssignment(
                        employeeId = employee.empId, salesOrderId = orderId, employeeName = employee.name,
                        department = employee.department, category = employee.category, plannedHours = 0.0
                    )).copy(plannedHours = requestedHours ?: existing?.plannedHours ?: 0.0)
                    if (existing == null) "Review the new labour assignment below, then save it."
                    else "I found ${employee.name}'s assignment. Review your changes below, then save it."
                }
            }
            normalized.contains("start") && normalized.contains("timer") -> {
                if (employeeId == null || orderId == null) {
                    "Say: start timer of employee EMP-001 in sales order SO-001."
                } else if (viewModel.startVoiceTimer(employeeId, orderId)) {
                    lastControlledOrderId = orderId
                    "Started the timer for $employeeId on $orderId and selected that employee in Supervisor."
                } else {
                    "I couldn't find employee $employeeId or sales order $orderId."
                }
            }
            normalized.startsWith("stop") && normalized.contains("timer") || normalized == "stop it" -> {
                val targetOrder = orderId ?: lastControlledOrderId.ifBlank { null }
                if (targetOrder == null) {
                    "Tell me which sales order to stop, for example: stop timer for sales order SO-001."
                } else if (viewModel.stopVoiceTimer(targetOrder)) {
                    lastControlledOrderId = targetOrder
                    "Paused $targetOrder, saved the command, and reset its timer to 00:00:00."
                } else {
                    "I couldn't find sales order $targetOrder."
                }
            }
            normalized.contains("create") && (normalized.contains("sales order") || normalized.contains("sale order")) -> {
                val newId = orderId ?: generateNextSalesOrderId(salesOrders)
                val item = field("item").orEmpty().ifBlank { "Voice Created Item" }
                val department = field("department")
                    ?: departments.firstOrNull()?.name.orEmpty().ifBlank { "Unassigned" }
                val quantity = Regex("(?:quantity|target)\\s+(\\d+)", RegexOption.IGNORE_CASE)
                    .find(normalized)?.groupValues?.getOrNull(1)?.toIntOrNull() ?: 1
                val hours = Regex("(?:planned\\s+)?hours\\s+(\\d+(?:\\.\\d+)?)", RegexOption.IGNORE_CASE)
                    .find(normalized)?.groupValues?.getOrNull(1)?.toDoubleOrNull() ?: 0.0
                val budget = Regex("budget\\s+(\\d+(?:\\.\\d+)?)", RegexOption.IGNORE_CASE)
                    .find(normalized)?.groupValues?.getOrNull(1)?.toDoubleOrNull() ?: 0.0
                viewModel.addNewTask(newId, item, quantity, department, "Created by ShopFloor Assistant", hours, budget)
                "Created sales order $newId for $item in $department with target quantity $quantity."
            }
            normalized.contains("create") && normalized.contains("employee") -> {
                val newId = employeeId ?: generateNextEmployeeId(employees)
                val name = field("name").orEmpty().ifBlank { newId }
                val requestedDept = field("department").orEmpty()
                val requestedCat = field("category").orEmpty()
                val department = departments.firstOrNull { it.code.equals(requestedDept, true) || it.name.equals(requestedDept, true) }
                val category = categories.firstOrNull { it.code.equals(requestedCat, true) || it.name.equals(requestedCat, true) }
                when {
                    requestedDept.isBlank() -> "Department is required. Select an existing department."
                    department == null -> "Department '$requestedDept' is not saved."
                    requestedCat.isBlank() -> "Labour category is required. Select an existing category."
                    category == null -> "Labour category '$requestedCat' is not saved."
                    category.department.isNotBlank() && !category.department.equals(department.name, true) && !category.department.equals(department.code, true) -> "Category ${category.name} does not belong to department ${department.name}."
                    else -> { viewModel.addNewEmployee(newId,name,department.name,category.name,"Unassigned","Active",category.hourlyRate,"Standard"); "Created employee $name with ID $newId in ${department.name}." }
                }
            }
            normalized.contains("create") && normalized.contains("department") -> {
                val code = Regex("department(?:\\s+code)?\\s+([a-z0-9-]+)", RegexOption.IGNORE_CASE)
                    .find(normalized)?.groupValues?.getOrNull(1)?.uppercase() ?: generateNextDepartmentId(departments)
                val name = field("name") ?: code
                viewModel.addNewDepartment(code, name, "Created by ShopFloor Assistant")
                "Created department $name with code $code."
            }
            normalized.contains("create") && normalized.contains("category") -> {
                val code = Regex("category(?:\\s+code)?\\s+([a-z0-9-]+)", RegexOption.IGNORE_CASE)
                    .find(normalized)?.groupValues?.getOrNull(1)?.uppercase() ?: generateNextCategoryId(categories)
                val name = field("name") ?: code
                val department = field("department") ?: departments.firstOrNull()?.name.orEmpty()
                val rate = Regex("rate\\s+(\\d+(?:\\.\\d+)?)", RegexOption.IGNORE_CASE)
                    .find(normalized)?.groupValues?.getOrNull(1)?.toDoubleOrNull() ?: 0.0
                viewModel.addNewCategory(code, name, rate, emptyList(), department)
                "Created category $name with code $code in $department."
            }
            mentionedOrder != null && listOf("detail", "status", "hour", "cost", "plan", "progress").any(normalized::contains) -> {
                val assigned = employees.filter { it.task.equals(mentionedOrder.id, true) }
                val actualHours = assigned.sumOf { it.hoursClocked }
                val actualCost = assigned.sumOf { it.hoursClocked * it.hourlyRate }
                "${mentionedOrder.id} (${mentionedOrder.item}) is ${mentionedOrder.status}: ${mentionedOrder.completedQty}/${mentionedOrder.targetQty} completed, ${String.format("%.2f", actualHours)}/${String.format("%.2f", mentionedOrder.plannedManhours)} hours, and ${formatCostValue(actualCost)}/${formatCostValue(mentionedOrder.plannedBudget)} cost with ${assigned.size} employee(s)."
            }
            mentionedEmployee != null && listOf("detail", "status", "hour", "department", "category", "assigned", "working").any(normalized::contains) ->
                "${mentionedEmployee.name} (${mentionedEmployee.empId}) is ${mentionedEmployee.status}, in ${mentionedEmployee.department} / ${mentionedEmployee.category}, assigned to ${mentionedEmployee.task}, with ${String.format("%.2f", mentionedEmployee.hoursClocked)} actual hours at ${formatCostValue(mentionedEmployee.hourlyRate)} per hour."
            (normalized.contains("exceed") || normalized.contains("over plan") || normalized.contains("over budget")) -> {
                val exceeded = salesOrders.mapNotNull { order ->
                    val assigned = employees.filter { it.task.equals(order.id, true) }
                    val actualHours = assigned.sumOf { it.hoursClocked }
                    val actualCost = assigned.sumOf { it.hoursClocked * it.hourlyRate }
                    val reasons = buildList {
                        if (order.plannedManhours > 0 && actualHours > order.plannedManhours) add("hours ${String.format("%.2f", actualHours)}/${String.format("%.2f", order.plannedManhours)}")
                        if (order.plannedBudget > 0 && actualCost > order.plannedBudget) add("cost ${formatCostValue(actualCost)}/${formatCostValue(order.plannedBudget)}")
                    }
                    if (reasons.isEmpty()) null else "${order.id}: ${reasons.joinToString()}"
                }
                if (exceeded.isEmpty()) "No sales order currently exceeds its planned hours or cost." else "Exceeded plans: ${exceeded.joinToString("; ")}"
            }
            normalized.contains("total") && normalized.contains("cost") -> {
                val total = employees.sumOf { it.hoursClocked * it.hourlyRate }
                "Current total actual labour cost is ${formatCostValue(total)} across ${salesOrders.size} sales order(s)."
            }
            normalized.contains("total") && (normalized.contains("hour") || normalized.contains("manhour")) ->
                "Current total actual labour time is ${String.format("%.2f", employees.sumOf { it.hoursClocked })} hours."
            normalized.contains("backup") || normalized.contains("database") ->
                "All operational records are saved in the local Room database and mirrored to ${viewModel.localBackupLocation()}. Deleted records are retained for 30 days."
            normalized.contains("report", true) ->
                "Open the Report tab to export PDF, Excel, Word, or CSV, or use Print PDF for A4 print preview."
            normalized.contains("employee", true) ->
                "There are ${employees.size} employees. Manage them from Master > Employees or assign them in Supervisor."
            normalized.contains("sales", true) || normalized.contains("order", true) ->
                "There are ${salesOrders.size} sales orders. Open Home for summaries or Master > Sales Orders for details."
            normalized.contains("timer", true) ->
                "Use the Supervisor page to start or stop a sales-order timer. Stopping pauses and resets it to zero."
            else -> "I can help you find reports, employees, sales orders, departments, costs, and supervisor timers."
        }
        messages.add(answer)
        input = ""
    }

    fun submitMessage(message: String) {
        val question = message.trim()
        if (question.isEmpty() || isThinking) return
        messages.add("You: $question")
        input = ""
        if (geminiApiKey.isBlank()) {
            executeMessage(question, false)
            return
        }
        isThinking = true
        val normalizedQuestion = resolveSpokenEntityIds(question, salesOrders, employees)
        val liveData = buildAssistantLiveData(salesOrders, employees, departments, categories, assignments)
        scope.launch {
            val interpreted = runCatching {
                withContext(Dispatchers.IO) {
                    AssistantAiClient.interpretWithGemini(geminiApiKey, normalizedQuestion, liveData)
                }
            }
            isThinking = false
            interpreted.onSuccess { result ->
                if (result.type != "answer") {
                    val fields = org.json.JSONObject(result.fields.toString())
                    when (result.type) {
                        "order" -> if (fields.optString("code").isBlank()) {
                            fields.put("code", generateNextSalesOrderId(salesOrders))
                        }
                        "emp" -> if (fields.optString("empId").isBlank()) {
                            fields.put("empId", generateNextEmployeeId(employees))
                        }
                        "department" -> if (fields.optString("code").isBlank()) {
                            fields.put("code", generateNextDepartmentId(departments))
                        }
                        "category" -> if (fields.optString("code").isBlank()) {
                            fields.put("code", generateNextCategoryId(categories))
                        }
                    }
                    pendingAction = result.copy(fields = fields)
                    messages.add("I understood this as ${result.type.replace("edit", "edit ").replace("work", " work")}. Check every field below before I process it.")
                    return@onSuccess
                }
                val f = result.fields
                fun value(name: String) = f.optString(name).trim()
                when (result.type) {
                    "answer" -> messages.add(result.answer.ifBlank { "I don't have enough live data to answer that." })
                    "startwork" -> {
                        val ids = buildList { val a = f.optJSONArray("empIds"); if (a != null) for (i in 0 until a.length()) add(a.optString(i)) }
                        val so = value("soId")
                        if (viewModel.startVoiceWork(so, ids)) { lastControlledOrderId = so; onOpenSupervisor(); messages.add("Opened Supervisor and started work on $so.") }
                        else messages.add("I couldn't match that sales order and its assigned employees.")
                    }
                    "stopwork" -> {
                        val so = value("soId").ifBlank { lastControlledOrderId }
                        if (viewModel.stopVoiceTimer(so)) { onOpenSupervisor(); messages.add("Stopped $so and saved actual employee hours.") }
                        else messages.add("I couldn't find a running sales order to stop.")
                    }
                    "shiftassign" -> {
                        val emp = value("empId"); val target = value("toSoId")
                        if (employees.any { it.empId.equals(emp, true) } && salesOrders.any { it.id.equals(target, true) }) {
                            viewModel.moveEmployees(listOf(emp), target); messages.add("Moved $emp to $target.")
                        } else messages.add("I couldn't match the employee and destination order exactly.")
                    }
                    "editorder" -> {
                        val old = salesOrders.firstOrNull { it.id.equals(value("soId"), true) }
                        if (old == null) messages.add("I couldn't match that existing sales order.") else {
                            viewModel.updateSalesOrder(old.copy(
                                item = value("customer").ifBlank { old.item }, description = value("desc").ifBlank { old.description },
                                plannedManhours = value("phrs").toDoubleOrNull() ?: old.plannedManhours,
                                plannedBudget = value("budget").toDoubleOrNull() ?: old.plannedBudget,
                                startDate = value("start").ifBlank { old.startDate }, endDate = value("end").ifBlank { old.endDate },
                                status = value("status").ifBlank { old.status }
                            )); messages.add("Updated ${old.id}.")
                        }
                    }
                    "editcategory" -> {
                        val old = categories.firstOrNull { it.code.equals(value("code"), true) || it.name.equals(value("name"), true) }
                        if (old == null) messages.add("I couldn't match that labour category.") else {
                            viewModel.updateLabourCategory(old.copy(name = value("name").ifBlank { old.name }, hourlyRate = value("rate").toDoubleOrNull() ?: old.hourlyRate), old.code)
                            messages.add("Updated labour category ${old.name}.")
                        }
                    }
                    "editdepartment" -> {
                        val old = departments.firstOrNull { it.code.equals(value("code"), true) || it.name.equals(value("name"), true) }
                        if (old == null) messages.add("I couldn't match that department.") else {
                            viewModel.updateDepartment(old.copy(name = value("name").ifBlank { old.name }, description = value("desc").ifBlank { old.description }), old.code)
                            messages.add("Updated department ${old.name}.")
                        }
                    }
                    "assign", "editassign" -> {
                        val employee = employees.firstOrNull { it.empId.equals(value("empId"), true) }
                        val order = salesOrders.firstOrNull { it.id.equals(value("soId"), true) }
                        if (employee == null || order == null) {
                            messages.add("I couldn't match the existing employee and sales order exactly.")
                        } else {
                            val existing = assignments.firstOrNull { it.employeeId.equals(employee.empId, true) && it.salesOrderId.equals(order.id, true) }
                            assignmentDraft = (existing ?: LabourAssignment(
                                employeeId = employee.empId, salesOrderId = order.id, employeeName = employee.name,
                                department = employee.department, category = employee.category, plannedHours = 0.0
                            )).copy(
                                plannedHours = value("phrs").toDoubleOrNull() ?: existing?.plannedHours ?: 0.0,
                                startDate = value("start").ifBlank { existing?.startDate.orEmpty() },
                                endDate = value("end").ifBlank { existing?.endDate.orEmpty() },
                                description = value("desc").ifBlank { existing?.description.orEmpty() },
                                status = value("status").ifBlank { existing?.status ?: "Assigned" }
                            )
                            messages.add("I matched ${employee.name} and ${order.id}. Review and save the assignment below.")
                        }
                    }
                    "order" -> {
                        val id = value("code").ifBlank { generateNextSalesOrderId(salesOrders) }.uppercase()
                        val customer = value("customer")
                        if (customer.isBlank()) messages.add("Please tell me the customer name before I create the sales order.")
                        else if (salesOrders.any { it.id.equals(id, true) }) messages.add("Sales order $id already exists.")
                        else {
                            salesOrderDraft = VoiceSalesOrderDraft(
                                code = id, customer = customer, description = value("desc"),
                                plannedHours = value("phrs"), budget = value("budget"),
                                startDate = value("start"), endDate = value("end"),
                                status = value("status").ifBlank { "Not Started" }
                            )
                            messages.add("I captured the sales order details. Review every field below, then save or discard it.")
                        }
                    }
                    "emp" -> {
                        val id = value("empId").ifBlank { generateNextEmployeeId(employees) }.uppercase()
                        val name = value("name")
                        if (name.isBlank()) messages.add("Please tell me the employee's name before I create the record.")
                        else {
                            val category = categories.firstOrNull { it.name.equals(value("cat"), true) || it.code.equals(value("cat"), true) }
                            viewModel.addNewEmployee(id, name, value("dept"), category?.name ?: value("cat"), "Unassigned", value("status").ifBlank { "Active" }, category?.hourlyRate ?: 0.0, value("skill").ifBlank { "Intermediate" })
                            messages.add("Created employee $name with ID $id.")
                        }
                    }
                    "category" -> {
                        val code = value("code").ifBlank { "CAT-${System.currentTimeMillis().toString().takeLast(4)}" }.uppercase()
                        val name = value("name")
                        if (name.isBlank()) messages.add("Please tell me the category name.") else {
                            viewModel.addNewCategory(code, name, value("rate").toDoubleOrNull() ?: 0.0, emptyList(), "")
                            messages.add("Created labour category $name ($code).")
                        }
                    }
                    "department" -> {
                        val code = value("code").ifBlank { "DEP-${System.currentTimeMillis().toString().takeLast(4)}" }.uppercase()
                        val name = value("name")
                        if (name.isBlank()) messages.add("Please tell me the department name.") else {
                            viewModel.addNewDepartment(code, name, value("desc")); messages.add("Created department $name ($code).")
                        }
                    }
                    else -> messages.add("I couldn't determine a safe action. Mention an exact employee, order, and action, then try again.")
                }
            }
                .onFailure {
                    messages.add("Gemini is unavailable, so I used offline command matching.")
                    executeMessage(question, false)
                }
        }
    }

    val speechLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val candidates = result.data
                ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                .orEmpty()
            selectBestSpeechResult(candidates, salesOrders, employees)?.let(::submitMessage)
        }
    }

    fun stopAndTranscribe() {
        val audio = recordingFile
        runCatching { recorder?.stop() }
        recorder?.release()
        recorder = null
        isRecording = false
        if (audio == null || !audio.exists()) return
        isThinking = true
        scope.launch {
            val transcript = runCatching {
                withContext(Dispatchers.IO) { AssistantAiClient.transcribeWithSarvam(sarvamApiKey, audio) }
            }
            audio.delete()
            isThinking = false
            transcript.onSuccess { spokenText ->
                val resolved = resolveSpokenEntityIds(spokenText, salesOrders, employees)
                if (resolved.isNotBlank()) submitMessage(resolved)
                else messages.add("I couldn't hear a complete command. Please try again.")
            }.onFailure {
                messages.add("I couldn't transcribe that recording. Check the Sarvam key and network, then try again.")
            }
        }
    }

    fun startSarvamRecording() {
        val file = File(context.cacheDir, "assistant_${System.currentTimeMillis()}.m4a")
        val mediaRecorder = if (android.os.Build.VERSION.SDK_INT >= 31) MediaRecorder(context) else @Suppress("DEPRECATION") MediaRecorder()
        runCatching {
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            mediaRecorder.setAudioSamplingRate(16000)
            mediaRecorder.setOutputFile(file.absolutePath)
            mediaRecorder.prepare()
            mediaRecorder.start()
        }.onSuccess {
            recorder = mediaRecorder
            recordingFile = file
            isRecording = true
            scope.launch {
                val recordingStartedAt = System.currentTimeMillis()
                var heardSpeech = false
                var lastSpeechAt = recordingStartedAt
                while (isRecording && recorder === mediaRecorder) {
                    kotlinx.coroutines.delay(250L)
                    val now = System.currentTimeMillis()
                    val amplitude = runCatching { mediaRecorder.maxAmplitude }.getOrDefault(0)
                    if (amplitude >= 900) {
                        heardSpeech = true
                        lastSpeechAt = now
                    }
                    val silentForMs = now - if (heardSpeech) lastSpeechAt else recordingStartedAt
                    if (silentForMs >= 3_000L) {
                        messages.add(
                            if (heardSpeech) "Speech finished after 3 seconds of silence. Sending it to the assistant."
                            else "No speech detected after 3 seconds. Microphone stopped."
                        )
                        stopAndTranscribe()
                        break
                    }
                    if (now - recordingStartedAt >= 25_000L) {
                        messages.add("Recording stopped automatically at 25 seconds for best speech accuracy.")
                        stopAndTranscribe()
                        break
                    }
                }
            }
        }.onFailure {
            mediaRecorder.release()
            messages.add("Microphone recording could not start.")
        }
    }

    val microphonePermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted -> if (granted) startSarvamRecording() else messages.add("Microphone permission is required for voice commands.") }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = isOpen,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 168.dp)
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .widthIn(max = 370.dp)
                    .heightIn(max = 470.dp),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                border = BorderStroke(1.dp, botAccent.copy(alpha = 0.72f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 22.dp)
            ) {
                Column(
                    modifier = Modifier.background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF32115C), botBackground, Color(0xFF160526))
                        )
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Brush.horizontalGradient(listOf(Color(0xFF3B176B), Color(0xFF7C3AED))))
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    Brush.linearGradient(listOf(botAccent, Color(0xFFA855F7), Color(0xFF6D28D9))),
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.SmartToy, contentDescription = null, tint = Color.White)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("ShopFloor Assistant", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("● Online", color = Color(0xFF22C55E), fontSize = 11.sp)
                        }
                        IconButton(onClick = { showApiSettings = !showApiSettings }) {
                            Icon(Icons.Filled.Key, contentDescription = "API key settings", tint = botAccent)
                        }
                        IconButton(onClick = { isOpen = false }) {
                            Icon(Icons.Filled.Close, contentDescription = "Minimize assistant", tint = botTextMuted)
                        }
                    }
                    HorizontalDivider(color = botAccent.copy(alpha = 0.45f))
                    AnimatedVisibility(visible = showApiSettings) {
                        Column(
                            modifier = Modifier.fillMaxWidth().background(botSurface).padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("SARVAM API KEY", color = botAccent, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                            OutlinedTextField(
                                value = sarvamApiKey, onValueChange = { sarvamApiKey = it.trim() },
                                placeholder = { Text("Paste your Sarvam API key") }, visualTransformation = PasswordVisualTransformation(),
                                singleLine = true, modifier = Modifier.fillMaxWidth(), colors = botFieldColors
                            )
                            Text("GEMINI API KEY", color = botAccent, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                            OutlinedTextField(
                                value = geminiApiKey, onValueChange = { geminiApiKey = it.trim() },
                                placeholder = { Text("Paste your Gemini API key") }, visualTransformation = PasswordVisualTransformation(),
                                singleLine = true, modifier = Modifier.fillMaxWidth(), colors = botFieldColors
                            )
                            Button(
                                onClick = {
                                    keyPreferences.edit().putString("sarvam", sarvamApiKey).putString("gemini", geminiApiKey).apply()
                                    showApiSettings = false
                                    Toast.makeText(context, "Assistant API keys saved on this device", Toast.LENGTH_SHORT).show()
                                }, modifier = Modifier.align(Alignment.End),
                                colors = ButtonDefaults.buttonColors(containerColor = botAccent, contentColor = botSurface)
                            ) { Text("Save keys") }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f, fill = false)
                            .verticalScroll(rememberScrollState())
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        messages.takeLast(6).forEach { message ->
                            val fromUser = message.startsWith("You:")
                            Box(
                                modifier = Modifier
                                    .align(if (fromUser) Alignment.End else Alignment.Start)
                                    .fillMaxWidth(if (fromUser) 0.82f else 0.94f)
                                    .background(
                                        if (fromUser) Color(0xFF6D28D9) else botSurfaceRaised.copy(alpha = 0.94f),
                                        RoundedCornerShape(
                                            topStart = 16.dp,
                                            topEnd = 16.dp,
                                            bottomStart = if (fromUser) 16.dp else 4.dp,
                                            bottomEnd = if (fromUser) 4.dp else 16.dp
                                        )
                                    )
                                    .border(
                                        1.dp,
                                        if (fromUser) botAccent.copy(alpha = 0.28f) else botBorder.copy(alpha = 0.38f),
                                        RoundedCornerShape(16.dp)
                                    )
                                    .padding(11.dp)
                            ) {
                                Text(message.removePrefix("You: "), color = botText, fontSize = 12.sp)
                            }
                        }
                        pendingAction?.let { pending ->
                            val fieldLabels = mapOf(
                                "code" to when (pending.type) {
                                    "order" -> "Sales Order ID"
                                    "department" -> "Department ID"
                                    "category" -> "Category ID"
                                    else -> "ID"
                                }, "soId" to "Sales Order ID", "fromSoId" to "From Order ID", "toSoId" to "To Order ID",
                                "empId" to "Employee ID", "empIds" to "Employee IDs", "customer" to "Customer", "name" to "Name",
                                "dept" to "Department", "cat" to "Category", "skill" to "Skill", "desc" to "Description",
                                "phrs" to "Planned Hrs", "budget" to "Budget (₹)", "rate" to "Hourly Rate (₹)",
                                "hours" to "Actual Hours", "qty" to "Completed Quantity",
                                "start" to "Start", "end" to "End", "status" to "Status"
                            )
                            Card(
                                colors = CardDefaults.cardColors(containerColor = botSurfaceRaised),
                                border = BorderStroke(1.dp, botBorder.copy(alpha = 0.7f)), shape = RoundedCornerShape(14.dp)
                            ) {
                                Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
                                    Text(pending.type.replaceFirstChar { it.uppercase() }.replace("work", " Work"), color = Color(0xFF4ADE80), fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
                                    val keys = pending.fields.keys().asSequence().filter { it != "type" && it != "text" }.toList()
                                    keys.forEach { key ->
                                        val current = if (key == "empIds") {
                                            val a = pending.fields.optJSONArray(key); if (a == null) "" else (0 until a.length()).joinToString(", ") { a.optString(it) }
                                        } else pending.fields.optString(key)
                                        OutlinedTextField(
                                            value = current,
                                            onValueChange = { changed ->
                                                val copy = org.json.JSONObject(pending.fields.toString())
                                                if (key == "empIds") copy.put(key, org.json.JSONArray(changed.split(",").map { it.trim() }.filter { it.isNotBlank() }))
                                                else copy.put(key, changed)
                                                pendingAction = pending.copy(fields = copy)
                                            },
                                            label = { Text(fieldLabels[key] ?: key) }, singleLine = key != "desc",
                                            modifier = Modifier.fillMaxWidth(), colors = botFieldColors
                                        )
                                    }
                                    Text("Nothing changes until you confirm.", color = botTextMuted, fontSize = 9.sp)
                                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                        TextButton(onClick = { pendingAction = null }) { Text("Discard") }
                                        Button(onClick = {
                                            val f = pending.fields
                                            fun v(key: String) = f.optString(key).trim()
                                            val successMessage = when (pending.type) {
                                                "order" -> {
                                                    val id = v("code").ifBlank { generateNextSalesOrderId(salesOrders) }.uppercase()
                                                    if (v("customer").isBlank()) "Customer name is required."
                                                    else if (salesOrders.any { it.id.equals(id, true) }) "Sales order $id already exists."
                                                    else { viewModel.addNewTask(id, v("customer"), 1, "Unassigned", v("desc"), v("phrs").toDoubleOrNull() ?: 0.0, v("budget").toDoubleOrNull() ?: 0.0, v("start"), v("end"), v("status").ifBlank { "Not Started" }); "Created $id for ${v("customer")}." }
                                                }
                                                "editorder" -> salesOrders.firstOrNull { it.id.equals(v("soId"), true) }?.let { old ->
                                                    viewModel.updateSalesOrder(old.copy(item=v("customer").ifBlank { old.item }, description=v("desc").ifBlank { old.description }, plannedManhours=v("phrs").toDoubleOrNull()?:old.plannedManhours, plannedBudget=v("budget").toDoubleOrNull()?:old.plannedBudget, startDate=v("start").ifBlank { old.startDate }, endDate=v("end").ifBlank { old.endDate }, status=v("status").ifBlank { old.status })); "Updated ${old.id}."
                                                } ?: "Sales order not found."
                                                "emp" -> {
                                                    val id = v("empId").ifBlank { generateNextEmployeeId(employees) }.uppercase()
                                                    val department = departments.firstOrNull { it.code.equals(v("dept"), true) || it.name.equals(v("dept"), true) }
                                                    val category = categories.firstOrNull { it.code.equals(v("cat"), true) || it.name.equals(v("cat"), true) }
                                                    when {
                                                        v("name").isBlank() -> "Employee name is required."
                                                        v("dept").isBlank() -> "Department is required. Select an existing department."
                                                        department == null -> "Department '${v("dept")}' is not saved. Create it first or select an existing department."
                                                        v("cat").isBlank() -> "Labour category is required. Select an existing category."
                                                        category == null -> "Labour category '${v("cat")}' is not saved. Create it first or select an existing category."
                                                        category.department.isNotBlank() && !category.department.equals(department.name, true) && !category.department.equals(department.code, true) -> "Category ${category.name} does not belong to department ${department.name}."
                                                        else -> { viewModel.addNewEmployee(id,v("name"),department.name,category.name,"Unassigned",v("status").ifBlank{"Active"},category.hourlyRate,v("skill").ifBlank{"Intermediate"}); "Created employee ${v("name")} ($id) in ${department.name} as ${category.name}." }
                                                    }
                                                }
                                                "editemp" -> employees.firstOrNull { it.empId.equals(v("empId"), true) }?.let { old ->
                                                    val requestedDept = v("dept").ifBlank { old.department }
                                                    val requestedCat = v("cat").ifBlank { old.category }
                                                    val department = departments.firstOrNull { it.code.equals(requestedDept, true) || it.name.equals(requestedDept, true) }
                                                    val category = categories.firstOrNull { it.code.equals(requestedCat, true) || it.name.equals(requestedCat, true) }
                                                    when {
                                                        department == null -> "Department '$requestedDept' is not saved."
                                                        category == null -> "Labour category '$requestedCat' is not saved."
                                                        category.department.isNotBlank() && !category.department.equals(department.name, true) && !category.department.equals(department.code, true) -> "Category ${category.name} does not belong to department ${department.name}."
                                                        else -> { viewModel.updateEmployee(old.copy(name=v("name").ifBlank{old.name}, department=department.name, category=category.name, hourlyRate=category.hourlyRate, skillLevel=v("skill").ifBlank{old.skillLevel}, status=v("status").ifBlank{old.status}, task=v("soId").ifBlank{old.task})); "Updated employee ${old.empId}." }
                                                    }
                                                } ?: "Employee not found."
                                                "assign", "editassign" -> { val emp=employees.firstOrNull{it.empId.equals(v("empId"),true)}; val so=salesOrders.firstOrNull{it.id.equals(v("soId"),true)}; if(emp==null||so==null) "Employee or sales order not found." else { val old=assignments.firstOrNull{it.employeeId.equals(emp.empId,true)&&it.salesOrderId.equals(so.id,true)}; viewModel.saveAssignment(LabourAssignment(emp.empId,so.id,emp.name,emp.department,emp.category,v("phrs").toDoubleOrNull()?:old?.plannedHours?:0.0,v("start").ifBlank{old?.startDate.orEmpty()},v("end").ifBlank{old?.endDate.orEmpty()},v("desc").ifBlank{old?.description.orEmpty()},v("status").ifBlank{old?.status?:"Assigned"})); "Saved ${emp.name}'s assignment on ${so.id}." } }
                                                "startwork" -> { val a=f.optJSONArray("empIds"); val ids=if(a==null) emptyList() else (0 until a.length()).map{a.optString(it)}; if(viewModel.startVoiceWork(v("soId"),ids)){lastControlledOrderId=v("soId");onOpenSupervisor();"Started work on ${v("soId")}."}else"Could not start work; check the order and employees." }
                                                "stopwork" -> { if(viewModel.stopVoiceTimer(v("soId").ifBlank{lastControlledOrderId})){onOpenSupervisor();"Stopped work and saved actual hours."}else"Running order not found." }
                                                "shiftassign" -> { if(employees.any{it.empId.equals(v("empId"),true)}&&salesOrders.any{it.id.equals(v("toSoId"),true)}){viewModel.moveEmployees(listOf(v("empId")),v("toSoId"));"Moved ${v("empId")} to ${v("toSoId")}."}else"Employee or destination order not found." }
                                                "category" -> { val code=v("code").ifBlank{generateNextCategoryId(categories)}.uppercase(); if(v("name").isBlank())"Category name is required." else{viewModel.addNewCategory(code,v("name"),v("rate").toDoubleOrNull()?:0.0,emptyList(),"");"Created category ${v("name")} ($code)."} }
                                                "editcategory" -> categories.firstOrNull{it.code.equals(v("code"),true)||it.name.equals(v("name"),true)}?.let{old->viewModel.updateLabourCategory(old.copy(name=v("name").ifBlank{old.name},hourlyRate=v("rate").toDoubleOrNull()?:old.hourlyRate),old.code);"Updated category ${old.name}."}?:"Category not found."
                                                "department" -> { val code=v("code").ifBlank{generateNextDepartmentId(departments)}.uppercase(); if(v("name").isBlank())"Department name is required." else{viewModel.addNewDepartment(code,v("name"),v("desc"));"Created department ${v("name")} ($code)."} }
                                                "editdepartment" -> departments.firstOrNull{it.code.equals(v("code"),true)||it.name.equals(v("name"),true)}?.let{old->viewModel.updateDepartment(old.copy(name=v("name").ifBlank{old.name},description=v("desc").ifBlank{old.description}),old.code);"Updated department ${old.name}."}?:"Department not found."
                                                "deleteorder" -> salesOrders.firstOrNull { it.id.equals(v("soId"), true) }?.let { old -> viewModel.deleteSalesOrder(old.id); "Deleted sales order ${old.id}." } ?: "Sales order not found."
                                                "deleteemp" -> employees.firstOrNull { it.empId.equals(v("empId"), true) }?.let { old -> viewModel.deleteEmployee(old.empId); "Deleted employee ${old.name} (${old.empId})." } ?: "Employee not found."
                                                "deletedepartment" -> departments.firstOrNull { it.code.equals(v("code"), true) || it.name.equals(v("code"), true) }?.let { old -> viewModel.deleteDepartment(old.code); "Deleted department ${old.name}." } ?: "Department not found."
                                                "deletecategory" -> categories.firstOrNull { it.code.equals(v("code"), true) || it.name.equals(v("code"), true) }?.let { old -> viewModel.deleteLabourCategory(old.code); "Deleted category ${old.name}." } ?: "Category not found."
                                                "updatehours" -> employees.firstOrNull { it.empId.equals(v("empId"), true) }?.let { old -> val hours=v("hours").toDoubleOrNull(); if(hours==null||hours<0)"Enter valid actual hours." else{viewModel.updateEmployeeHours(old.empId,old.name,hours);"Updated ${old.name}'s actual hours to $hours."} } ?: "Employee not found."
                                                "updateprogress" -> salesOrders.firstOrNull { it.id.equals(v("soId"), true) }?.let { old -> val qty=v("qty").toIntOrNull(); if(qty==null||qty<0)"Enter a valid completed quantity." else{val safeQty=qty.coerceAtMost(old.targetQty);viewModel.updateSalesOrder(old.copy(completedQty=safeQty,status=v("status").ifBlank{old.status}));"Updated ${old.id} progress to $safeQty of ${old.targetQty}."} } ?: "Sales order not found."
                                                else -> "This action is not supported."
                                            }
                                            messages.add(successMessage)
                                            val validationFailed = successMessage.contains("required", true) ||
                                                successMessage.contains("not found", true) ||
                                                successMessage.contains("not saved", true) ||
                                                successMessage.contains("does not belong", true) ||
                                                successMessage.contains("already exists", true) ||
                                                successMessage.startsWith("Enter valid", true) ||
                                                successMessage.startsWith("Could not", true)
                                            if (!validationFailed) pendingAction = null
                                        }) { Text("Confirm") }
                                    }
                                }
                            }
                        }
                        salesOrderDraft?.let { draft ->
                            Card(
                                colors = CardDefaults.cardColors(containerColor = botSurfaceRaised),
                                border = BorderStroke(1.dp, botBorder),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
                                    Text("Review Sales Order", color = Color.White, fontWeight = FontWeight.Bold)
                                    OutlinedTextField(value = draft.code, onValueChange = { salesOrderDraft = draft.copy(code = it.uppercase()) }, label = { Text("Sales Order ID") }, singleLine = true)
                                    OutlinedTextField(value = draft.customer, onValueChange = { salesOrderDraft = draft.copy(customer = it) }, label = { Text("Customer name") }, singleLine = true)
                                    OutlinedTextField(value = draft.plannedHours, onValueChange = { salesOrderDraft = draft.copy(plannedHours = it) }, label = { Text("Total planned hours") }, singleLine = true)
                                    OutlinedTextField(value = draft.budget, onValueChange = { salesOrderDraft = draft.copy(budget = it) }, label = { Text("Budget (₹)") }, singleLine = true)
                                    OutlinedTextField(value = draft.description, onValueChange = { salesOrderDraft = draft.copy(description = it) }, label = { Text("Description") })
                                    OutlinedTextField(value = draft.startDate, onValueChange = { salesOrderDraft = draft.copy(startDate = it) }, label = { Text("Start date (YYYY-MM-DD)") }, singleLine = true)
                                    OutlinedTextField(value = draft.endDate, onValueChange = { salesOrderDraft = draft.copy(endDate = it) }, label = { Text("End date (YYYY-MM-DD)") }, singleLine = true)
                                    OutlinedTextField(value = draft.status, onValueChange = { salesOrderDraft = draft.copy(status = it) }, label = { Text("Status") }, singleLine = true)
                                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                        TextButton(onClick = { salesOrderDraft = null }) { Text("Discard") }
                                        Button(onClick = {
                                            if (draft.code.isBlank() || draft.customer.isBlank()) {
                                                messages.add("Sales Order ID and customer name are required.")
                                            } else if (salesOrders.any { it.id.equals(draft.code, true) }) {
                                                messages.add("Sales order ${draft.code} already exists.")
                                            } else {
                                                viewModel.addNewTask(
                                                    id = draft.code, item = draft.customer, targetQty = 1, department = "Unassigned",
                                                    description = draft.description, plannedManhours = draft.plannedHours.toDoubleOrNull() ?: 0.0,
                                                    plannedBudget = draft.budget.toDoubleOrNull() ?: 0.0, startDate = draft.startDate,
                                                    endDate = draft.endDate, status = draft.status.ifBlank { "Not Started" }
                                                )
                                                messages.add("Created ${draft.code} for ${draft.customer}: ${draft.plannedHours.ifBlank { "0" }} planned hours and ${formatCostValue(draft.budget.toDoubleOrNull() ?: 0.0)} budget.")
                                                salesOrderDraft = null
                                            }
                                        }) { Text("Create Sales Order") }
                                    }
                                }
                            }
                        }
                        assignmentDraft?.let { draft ->
                            Card(
                                colors = CardDefaults.cardColors(containerColor = botSurfaceRaised),
                                border = BorderStroke(1.dp, botBorder),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
                                    Text("${draft.employeeName} → ${draft.salesOrderId}", color = Color.White, fontWeight = FontWeight.Bold)
                                    Text("${draft.department} • ${draft.category}", color = Color(0xFF94A3B8), fontSize = 10.sp)
                                    OutlinedTextField(value = draft.plannedHours.toString(), onValueChange = { value -> assignmentDraft = draft.copy(plannedHours = value.toDoubleOrNull() ?: 0.0) }, label = { Text("Planned hours") }, singleLine = true)
                                    OutlinedTextField(value = draft.startDate, onValueChange = { assignmentDraft = draft.copy(startDate = it) }, label = { Text("Start date") }, singleLine = true)
                                    OutlinedTextField(value = draft.endDate, onValueChange = { assignmentDraft = draft.copy(endDate = it) }, label = { Text("End date") }, singleLine = true)
                                    OutlinedTextField(value = draft.description, onValueChange = { assignmentDraft = draft.copy(description = it) }, label = { Text("Description") })
                                    OutlinedTextField(value = draft.status, onValueChange = { assignmentDraft = draft.copy(status = it) }, label = { Text("Status") }, singleLine = true)
                                    Text("Actual hours are locked and updated only by the Supervisor timer.", color = Color(0xFF94A3B8), fontSize = 9.sp)
                                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                        TextButton(onClick = { assignmentDraft = null }) { Text("Cancel") }
                                        Button(onClick = {
                                            if (viewModel.saveAssignment(draft)) {
                                                messages.add("Assignment saved for ${draft.employeeName} on ${draft.salesOrderId}.")
                                                assignmentDraft = null
                                            }
                                        }) { Text("Save assignment") }
                                    }
                                }
                            }
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            listOf("Where are reports?", "How many employees?").forEach { prompt ->
                                Surface(
                                    modifier = Modifier.clickable { submitMessage(prompt) },
                                    shape = RoundedCornerShape(20.dp),
                                    color = botSurfaceSoft,
                                    border = BorderStroke(1.dp, botBorder.copy(alpha = 0.7f))
                                ) {
                                    Text(prompt, color = botText, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp))
                                }
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    listOf(Color(0xFF2E1065), Color(0xFF3B176B))
                                )
                            )
                            .border(BorderStroke(1.dp, botBorder.copy(alpha = 0.35f)))
                            .padding(horizontal = 10.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                if (sarvamApiKey.isNotBlank()) {
                                    if (isRecording) stopAndTranscribe()
                                    else if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) startSarvamRecording()
                                    else microphonePermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                                } else {
                                    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                                        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                                        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                                        putExtra(RecognizerIntent.EXTRA_PROMPT, "Say a ShopFloor command")
                                        putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5)
                                        putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false)
                                        putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 3_000L)
                                        putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 3_000L)
                                        putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 1_000L)
                                    }
                                    runCatching { speechLauncher.launch(intent) }
                                }
                            },
                            modifier = Modifier
                                .size(48.dp)
                                .background(
                                    brush = if (isRecording) {
                                        Brush.linearGradient(listOf(Color(0xFFDC2626), Color(0xFFF97316)))
                                    } else {
                                        Brush.linearGradient(listOf(botAccent, Color(0xFFFBBF24)))
                                    },
                                    shape = CircleShape
                                )
                                .border(
                                    1.dp,
                                    if (isRecording) Color(0xFFFECACA) else Color.White.copy(alpha = 0.68f),
                                    CircleShape
                                )
                        ) {
                            Icon(
                                Icons.Filled.Mic,
                                contentDescription = if (isRecording) "Stop recording" else "Start voice command",
                                tint = if (isRecording) Color.White else botSurface,
                                modifier = Modifier.size(23.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        BasicTextField(
                            value = input,
                            onValueChange = { input = it },
                            modifier = Modifier
                                .weight(1f)
                                .background(Color(0xFF1A082E), RoundedCornerShape(24.dp))
                                .border(1.dp, botAccent.copy(alpha = 0.52f), RoundedCornerShape(24.dp))
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            textStyle = TextStyle(color = botText, fontSize = 12.sp),
                            cursorBrush = SolidColor(botAccent),
                            singleLine = true,
                            decorationBox = { field ->
                                if (input.isBlank()) Text("Type a message...", color = botTextMuted.copy(alpha = 0.68f), fontSize = 12.sp)
                                field()
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = { submitMessage(input) },
                            enabled = input.isNotBlank() && !isThinking,
                            modifier = Modifier
                                .size(48.dp)
                                .background(
                                    if (input.isNotBlank() && !isThinking) botAccent else botTextMuted.copy(alpha = 0.24f),
                                    CircleShape
                                )
                                .border(1.dp, Color.White.copy(alpha = 0.42f), CircleShape)
                        ) {
                            Icon(
                                Icons.Filled.Send,
                                contentDescription = "Send message",
                                tint = if (input.isNotBlank() && !isThinking) botSurface else botTextMuted.copy(alpha = 0.62f)
                            )
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 18.dp, bottom = 108.dp)
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .size(60.dp)
                .background(
                    Brush.sweepGradient(listOf(botAccent, Color(0xFFA855F7), Color(0xFF6D28D9), botAccent)),
                    CircleShape
                )
                .border(2.dp, Color.White.copy(alpha = 0.82f), CircleShape)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                        // Keep the assistant above the app's bottom navigation bar.
                        offsetY = (offsetY + dragAmount.y).coerceAtMost(0f)
                    }
                }
                .clickable { isOpen = !isOpen },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.SmartToy, contentDescription = "Open ShopFloor Assistant", tint = Color.White, modifier = Modifier.size(28.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    authProvider: AuthProvider,
    viewModel: DashboardViewModel,
    onLogoutSuccess: () -> Unit
) {
    val appContext = LocalContext.current.applicationContext

    // Current user context
    val userEmail by authProvider.currentUserEmail.collectAsState()

    // Dashboard States
    val salesOrders by viewModel.salesOrders.collectAsState()
    val employees by viewModel.employees.collectAsState()
    val departmentsList by viewModel.departments.collectAsState()
    val categoriesList by viewModel.categories.collectAsState()
    val logs by viewModel.logs.collectAsState()
    val recoverableRecords by viewModel.recoverableRecords.collectAsState()
    val recycleRetentionDays by viewModel.recycleRetentionDays.collectAsState()
    val backupLocationLabel by viewModel.backupLocationLabel.collectAsState()

    // Filters
    val selectedDept by viewModel.selectedDepartment.collectAsState()
    val selectedTimeframe by viewModel.selectedTimeframe.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    // Bottom navigation tab state
    var activeTab by remember { mutableStateOf("Home") }
    var appLanguage by remember {
        mutableStateOf(
            runCatching {
                AppLanguage.valueOf(
                    appContext.getSharedPreferences("shop_floor_ui", android.content.Context.MODE_PRIVATE)
                        .getString("app_language", AppLanguage.ENGLISH.name) ?: AppLanguage.ENGLISH.name
                )
            }.getOrDefault(AppLanguage.ENGLISH)
        )
    }
    var glassThemeStyle by rememberSaveable {
        mutableStateOf(
            runCatching {
                GlassThemeStyle.valueOf(
                    appContext.getSharedPreferences("shop_floor_ui", android.content.Context.MODE_PRIVATE)
                        .getString("glass_theme", GlassThemeStyle.ROYAL_GLASS.name)
                        ?: GlassThemeStyle.ROYAL_GLASS.name
                )
            }.getOrDefault(GlassThemeStyle.ROYAL_GLASS)
        )
    }
    LaunchedEffect(glassThemeStyle) {
        appContext.getSharedPreferences("shop_floor_ui", android.content.Context.MODE_PRIVATE)
            .edit()
            .putString("glass_theme", glassThemeStyle.name)
            .apply()
    }
    LaunchedEffect(appLanguage) {
        activeOperationalLanguage.value = appLanguage
        UiLanguageRuntime.tag = appLanguage.tag
        appContext.getSharedPreferences("shop_floor_ui", android.content.Context.MODE_PRIVATE)
            .edit().putString("app_language", appLanguage.name).apply()
        Locale.setDefault(Locale.forLanguageTag(appLanguage.tag))
    }

    // Selected Sales Order for Details page
    var selectedOrderDetail by remember { mutableStateOf<SalesOrder?>(null) }

    // Profile is a full destination so settings remain readable on phones.
    var showProfilePage by rememberSaveable { mutableStateOf(false) }
    var languageMenuExpanded by remember { mutableStateOf(false) }
    var showLogoutConfirmation by remember { mutableStateOf(false) }
    var showRecycleBin by rememberSaveable { mutableStateOf(false) }
    var reportLocationLabel by remember {
        mutableStateOf(
            appContext.getSharedPreferences("shop_floor_ui", android.content.Context.MODE_PRIVATE)
                .getString("report_location_label", "Downloads/OCS Reports")
                ?: "Downloads/OCS Reports"
        )
    }
    val reportFolderPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            runCatching {
                appContext.contentResolver.takePersistableUriPermission(
                    selectedUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
            }
            val label = selectedUri.lastPathSegment
                ?.substringAfterLast(':')
                ?.ifBlank { "Selected folder" }
                ?: "Selected folder"
            appContext.getSharedPreferences("shop_floor_ui", android.content.Context.MODE_PRIVATE)
                .edit()
                .putString("report_tree_uri", selectedUri.toString())
                .putString("report_location_label", label)
                .apply()
            reportLocationLabel = label
            Toast.makeText(appContext, "Report location changed to $label", Toast.LENGTH_LONG).show()
        }
    }
    val backupFolderPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            val permissionFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            runCatching {
                appContext.contentResolver.takePersistableUriPermission(selectedUri, permissionFlags)
            }
            val label = selectedUri.lastPathSegment
                ?.substringAfterLast(':')
                ?.ifBlank { "Selected folder" }
                ?: "Selected folder"
            viewModel.setBackupLocation(selectedUri.toString(), label)
            Toast.makeText(appContext, "Backup folder changed to $label", Toast.LENGTH_LONG).show()
        }
    }

    // Dialog flags
    var showClockInDialog by remember { mutableStateOf(false) }
    var showLabourDialog by remember { mutableStateOf(false) }
    var showNewTaskDialog by remember { mutableStateOf(false) }

    // Selected item states for Dialog auto-fills
    var preselectedOrder by remember { mutableStateOf<SalesOrder?>(null) }
    var preselectedEmployee by remember { mutableStateOf<EmployeeActivity?>(null) }

    // Filter Logic
    val filteredOrders = salesOrders.filter { order ->
        val deptMatches = selectedDept == "All" || order.department.equals(selectedDept, ignoreCase = true)
        val searchMatches = searchQuery.isEmpty() || 
                order.id.contains(searchQuery, ignoreCase = true) || 
                order.item.contains(searchQuery, ignoreCase = true)
        deptMatches && searchMatches
    }

    val filteredEmployees = employees.filter { emp ->
        val deptMatches = selectedDept == "All" || emp.department.equals(selectedDept, ignoreCase = true)
        val searchMatches = searchQuery.isEmpty() || emp.name.contains(searchQuery, ignoreCase = true)
        deptMatches && searchMatches
    }

    val exceededPlanAlerts = salesOrders.flatMap { order ->
        val actualHours = getActualHrForOrder(order, employees)
        val actualCost = getActualCostForOrder(order, employees)
        buildList {
            if (order.plannedManhours > 0.0 && actualHours > order.plannedManhours) {
                add("${order.id} alert: Actual hours exceeded")
            }
            if (order.plannedBudget > 0.0 && actualCost > order.plannedBudget) {
                add("${order.id} alert: Actual cost exceeded")
            }
        }
    }

    // Recalculate KPIs
    val activeEmpCount = employees.filter { it.status == "Active" }.size
    val totalEmpCount = employees.size
    val completedJobsCount = salesOrders.filter { it.status == "Completed" }.size
    val totalJobsCount = salesOrders.size
    val totalClockedHours = employees.sumOf { it.hoursClocked }
    
    val totalTargetUnits = salesOrders.sumOf { it.targetQty }
    val totalCompletedUnits = salesOrders.sumOf { it.completedQty }
    val averageProductivity = if (totalTargetUnits > 0) {
        (totalCompletedUnits.toDouble() / totalTargetUnits.toDouble() * 100.0)
    } else {
        0.0
    }

    val isHomeDark = false
    val darkAccent = Color(0xFF24B9FF)
    val darkHighlight = Color(0xFF2563EB)
    val darkChrome = Color(0xFF06172F)
    val darkBase = Color(0xFF010713)
    val profileName = userEmail
        ?.substringBefore("@")
        ?.replaceFirstChar { first -> first.uppercase() }
        ?.ifBlank { "Supervisor" }
        ?: "Supervisor"

    // UI Content
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.ocs_mark),
                            contentDescription = "OCS logo",
                            modifier = Modifier.size(38.dp),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.widthIn(max = 210.dp)) {
                            Text(
                                "OCS SHOPFLOOR",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                                color = if (isHomeDark) Color.White else Color(0xFF2E1065)
                            )
                            Text(
                                uiText(appLanguage, "terminal"),
                                fontSize = 11.sp,
                                color = if (isHomeDark) Color(0xFFAFC6E7) else Color(0xFF5B3A75),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                },
                actions = {
                    // Profile Circle displaying first character of user's email
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .clickable { showProfilePage = true }
                            .background(if (isHomeDark) darkChrome else themeSurface(glassThemeStyle))
                            .border(1.dp, if (isHomeDark) darkAccent else themeCardBorder(glassThemeStyle), RoundedCornerShape(24.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                            .background(Brush.linearGradient(if (isHomeDark) listOf(Color(0xFF1E40AF), Color(0xFF24B9FF)) else listOf(Color(0xFF6D28D9), Color(0xFFA855F7)))),
                            contentAlignment = Alignment.Center
                        ) {
                            val initial = userEmail?.take(1)?.uppercase() ?: "U"
                            Text(
                                initial,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.widthIn(max = 150.dp)) {
                            Text(profileName, color = if (isHomeDark) Color.White else Color(0xFF2E1065), fontSize = 12.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(Modifier.size(6.dp).clip(CircleShape).background(Color(0xFF16A34A)))
                                Spacer(Modifier.width(4.dp))
                                Text(uiText(appLanguage, "online"), color = if (isHomeDark) Color(0xFFAFC6E7) else Color(0xFF67557A), fontSize = 9.sp)
                            }
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Open profile",
                            tint = if (isHomeDark) Color(0xFFAFC6E7) else Color(0xFF5B3A75),
                            modifier = Modifier.size(16.dp).graphicsLayer { rotationZ = 180f }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = if (isHomeDark) darkBase else Color.White.copy(alpha = 0.66f),
                    titleContentColor = if (isHomeDark) Color.White else Color(0xFF2E1065)
                )
            )
        },
        bottomBar = {
            if (!showProfilePage) {
            NavigationBar(
                containerColor = if (isHomeDark) darkChrome else themeSurface(glassThemeStyle),
                modifier = Modifier.border(BorderStroke(1.dp, if (isHomeDark) darkAccent else themeCardBorder(glassThemeStyle)))
            ) {
                val navColors = if (isHomeDark) {
                    NavigationBarItemDefaults.colors(
                        selectedIconColor = darkAccent,
                        selectedTextColor = darkAccent,
                        unselectedIconColor = Color(0xFFAFC6E7),
                        unselectedTextColor = Color(0xFFAFC6E7),
                        indicatorColor = darkHighlight.copy(alpha = 0.28f)
                    )
                } else {
                    NavigationBarItemDefaults.colors()
                }

                NavigationBarItem(
                    selected = activeTab == "Home",
                    onClick = { activeTab = "Home" },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text(uiText(appLanguage, "home"), fontSize = 11.sp, fontWeight = FontWeight.Bold, maxLines = 1) },
                    colors = navColors
                )
                NavigationBarItem(
                    selected = activeTab == "Master",
                    onClick = { activeTab = "Master" },
                    icon = { Icon(Icons.Filled.Build, contentDescription = "Master Registry") },
                    label = { Text(uiText(appLanguage, "master"), fontSize = 11.sp, fontWeight = FontWeight.Bold, maxLines = 1) },
                    colors = navColors
                )
                NavigationBarItem(
                    selected = activeTab == "Supervisor App",
                    onClick = { activeTab = "Supervisor App" },
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Supervisor App") },
                    label = { Text(uiText(appLanguage, "supervisor"), fontSize = 10.sp, fontWeight = FontWeight.Bold, maxLines = 1) },
                    colors = navColors
                )
                NavigationBarItem(
                    selected = activeTab == "Report",
                    onClick = { activeTab = "Report" },
                    icon = { Icon(Icons.Filled.DateRange, contentDescription = "Reports") },
                    label = { Text(uiText(appLanguage, "report"), fontSize = 11.sp, fontWeight = FontWeight.Bold, maxLines = 1) },
                    colors = navColors
                )
            }
            }
        },
        containerColor = if (isHomeDark) darkBase else Color(0xFFF7F2FF)
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
        if (showProfilePage) {
            ProfilePage(
                modifier = Modifier.padding(innerPadding),
                profileName = profileName,
                userEmail = userEmail.orEmpty(),
                managementAlertEmail = BuildConfig.MANAGEMENT_ALERT_EMAIL,
                appLanguage = appLanguage,
                glassThemeStyle = glassThemeStyle,
                recoverableCount = recoverableRecords.size,
                recycleRetentionDays = recycleRetentionDays,
                reportLocationLabel = reportLocationLabel,
                onBack = { showProfilePage = false },
                onLanguageChange = { appLanguage = it },
                onThemeChange = { glassThemeStyle = it },
                onOpenRecycleBin = {
                    viewModel.refreshRecycleBin()
                    showRecycleBin = true
                },
                onChangeReportFolder = { reportFolderPicker.launch(null) },
                onLogout = { showLogoutConfirmation = true }
            )
        } else {
        val navigationOrder = listOf("Home", "Master", "Supervisor App", "Report")
        AnimatedContent(
            targetState = activeTab,
            transitionSpec = {
                val movingForward =
                    navigationOrder.indexOf(targetState) >= navigationOrder.indexOf(initialState)
                val direction = if (movingForward) 1 else -1
                (
                    slideInHorizontally(
                        animationSpec = tween(420, easing = FastOutSlowInEasing),
                        initialOffsetX = { fullWidth -> direction * fullWidth / 4 }
                    ) + fadeIn(animationSpec = tween(320, easing = FastOutSlowInEasing))
                ).togetherWith(
                    slideOutHorizontally(
                        animationSpec = tween(360, easing = FastOutSlowInEasing),
                        targetOffsetX = { fullWidth -> -direction * fullWidth / 5 }
                    ) + fadeOut(animationSpec = tween(260, easing = FastOutSlowInEasing))
                )
            },
            label = "main-tab-transition"
        ) { displayedTab ->
        when (displayedTab) {
            "Home" -> {
                val currentSelectedOrder = selectedOrderDetail
                if (currentSelectedOrder != null) {
                    Box(modifier = Modifier.padding(innerPadding)) {
                        SalesOrderDetailsScreen(
                            order = currentSelectedOrder,
                            employees = employees,
                            onBack = { selectedOrderDetail = null }
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(glassBackground(glassThemeStyle))
                            .padding(innerPadding)
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Top Header Purple Card matching attached screenshot
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF6D28D9))
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Text(
                                    text = uiText(appLanguage, "labourRouting"),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = uiText(appLanguage, "labourRoutingSub"),
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontSize = 13.sp
                                )
                            }
                        }

                        AnimatedVisibility(visible = exceededPlanAlerts.isNotEmpty()) {
                            Card(
                                modifier = Modifier.fillMaxWidth().semantics {
                                    contentDescription = "Planned limit alert. ${exceededPlanAlerts.joinToString()}"
                                },
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF4E5)),
                                border = BorderStroke(1.dp, Color(0xFFF59E0B)),
                                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                            ) {
                                Row(Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
                                    Box(
                                        Modifier.size(40.dp).clip(RoundedCornerShape(12.dp)).background(Color(0xFFF59E0B)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(Icons.Filled.Warning, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
                                    }
                                    Spacer(Modifier.width(12.dp))
                                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        Text("Planned limit exceeded", color = Color(0xFF7C2D12), fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                                        exceededPlanAlerts.take(3).forEach { alert ->
                                            Text(alert, color = Color(0xFF7C2D12), fontSize = 11.sp, fontWeight = FontWeight.Medium)
                                        }
                                        if (exceededPlanAlerts.size > 3) {
                                            Text("+${exceededPlanAlerts.size - 3} more alerts", color = Color(0xFF9A3412), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }
                        }

                        // Dynamic calculations for Executive Metrics & Charts
                        val activeOrdersCount = filteredOrders.filter { it.status != "Completed" }.size
                        val archivedCompletedCount = filteredOrders.filter { it.status == "Completed" }.size

                        val totalPlannedBudget = filteredOrders.sumOf { it.plannedBudget }
                        val totalActualCost = filteredOrders.sumOf { getActualCostForOrder(it, filteredEmployees) }
                        val costVarianceVal = totalPlannedBudget - totalActualCost
                        val isFavorable = costVarianceVal >= 0
                        val varianceStr = formatCostValue(costVarianceVal)

                        val actualHoursVal = filteredOrders.sumOf { getActualHrForOrder(it, filteredEmployees) }
                        val plannedHoursVal = filteredOrders.sumOf { it.plannedManhours }

                        val totalTargetQty = filteredOrders.sumOf { it.targetQty }
                        val totalCompletedQty = filteredOrders.sumOf { it.completedQty }
                        val efficiencyVal = if (totalTargetQty > 0) (totalCompletedQty.toDouble() / totalTargetQty.toDouble() * 100.0) else 0.0

                        // Department Filter Header Dropdown
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = operationalText(appLanguage, "Shop Floor Executive Metrics").uppercase(),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF6D28D9),
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = if (selectedDept == "All") operationalText(appLanguage, "All Departments Active") else "${operationalText(appLanguage, "Department")}: $selectedDept",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF5B3A75)
                                )
                            }

                            var deptDropdownExpanded by remember { mutableStateOf(false) }
                            Box {
                                androidx.compose.material3.Surface(
                                    onClick = { deptDropdownExpanded = true },
                                    shape = RoundedCornerShape(20.dp),
                                    color = Color.White,
                                    border = BorderStroke(1.dp, Color(0xFFD8B4FE)),
                                    shadowElevation = 1.dp
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.FilterList,
                                            contentDescription = "Filter Department",
                                            tint = Color(0xFF6D28D9),
                                            modifier = Modifier.size(15.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = if (selectedDept == "All") operationalText(appLanguage, "All Depts") else selectedDept,
                                            color = Color(0xFF2E1065),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            imageVector = Icons.Filled.ArrowDropDown,
                                            contentDescription = "Dropdown",
                                            tint = Color(0xFF5B3A75),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }

                                 DropdownMenu(
                                    expanded = deptDropdownExpanded,
                                    onDismissRequest = { deptDropdownExpanded = false },
                                    modifier = Modifier
                                        .background(Color.White.copy(alpha = 0.64f))
                                        .border(1.dp, Color(0xFFD8B4FE))
                                ) {
                                    val deptOptions = (listOf("All") + departmentsList.map { it.name }).distinct()
                                    deptOptions.forEach { dept ->
                                        DropdownMenuItem(
                                            text = {
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    if (selectedDept == dept || (selectedDept == "All" && dept == "All")) {
                                                        Icon(
                                                            imageVector = Icons.Filled.Check,
                                                            contentDescription = null,
                                                            tint = Color(0xFF6D28D9),
                                                            modifier = Modifier.size(16.dp)
                                                        )
                                                        Spacer(modifier = Modifier.width(8.dp))
                                                    }
                                                    Text(
                                                        text = if (dept == "All") operationalText(appLanguage, "All Departments") else dept,
                                                        fontWeight = if (selectedDept == dept) FontWeight.Bold else FontWeight.Normal,
                                                        color = if (selectedDept == dept) Color(0xFF6D28D9) else Color(0xFF2E1065),
                                                        fontSize = 13.sp
                                                    )
                                                }
                                            },
                                            onClick = {
                                                viewModel.setDepartment(dept)
                                                deptDropdownExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        // EXECUTIVE KPI CARDS (Row 1 & Row 2 in Light Theme)
                        Row(
                            modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            SleekKpiCardLight(
                                title = operationalText(appLanguage, "Active Orders").uppercase(),
                                value = "$activeOrdersCount",
                                sub = operationalText(appLanguage, "{count} order archived completed").replace("{count}", archivedCompletedCount.toString()),
                                icon = Icons.Filled.Assignment,
                                accentColor = if (isHomeDark) darkAccent else Color(0xFFA855F7),
                                dark = isHomeDark,
                                modifier = Modifier.weight(1f).fillMaxHeight()
                            )

                            SleekKpiCardLight(
                                title = operationalText(appLanguage, "Cost Variance").uppercase(),
                                value = if (totalPlannedBudget > 0) varianceStr else formatCostValue(0.0),
                                sub = if (isFavorable) "↓ ${operationalText(appLanguage, "Favorable (Under budget)")}" else "↑ ${operationalText(appLanguage, "Unfavorable (Over budget)")}",
                                subColor = if (isFavorable) Color(0xFF2E7D32) else Color(0xFFB00020),
                                icon = Icons.Filled.AttachMoney,
                                accentColor = Color(0xFF2E7D32),
                                dark = isHomeDark,
                                modifier = Modifier.weight(1f).fillMaxHeight()
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            SleekKpiCardLight(
                                title = operationalText(appLanguage, "Labour Utilization").uppercase(),
                                value = "${String.format(Locale.US, "%.1f", actualHoursVal)} ${operationalText(appLanguage, "hrs")}",
                                sub = operationalText(appLanguage, "Planned limit: {hours} manhours").replace("{hours}", String.format(Locale.US, "%.1f", plannedHoursVal)),
                                icon = Icons.Filled.Schedule,
                                accentColor = Color(0xFFF59E0B),
                                dark = isHomeDark,
                                modifier = Modifier.weight(1f).fillMaxHeight()
                            )

                            SleekKpiCardLight(
                                title = operationalText(appLanguage, "Labour Efficiency").uppercase(),
                                value = "${String.format(Locale.US, "%.1f", efficiencyVal)}%",
                                sub = operationalText(appLanguage, "Overall Productivity Index"),
                                icon = Icons.Filled.ShowChart,
                                accentColor = if (isHomeDark) darkAccent else Color(0xFF6D28D9),
                                dark = isHomeDark,
                                modifier = Modifier.weight(1f).fillMaxHeight()
                            )
                        }

                        // CHARTS SECTION (Light Theme)
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            ActualVsPlannedCostCardLight(orders = filteredOrders, employees = filteredEmployees, dark = isHomeDark)
                            CostByDepartmentCardLight(orders = filteredOrders, employees = filteredEmployees, selectedDept = selectedDept, dark = isHomeDark)
                            DailyLabourDeploymentCardLight(orders = filteredOrders, employees = filteredEmployees, dark = isHomeDark)
                            ManhoursByCategoryCardLight(employees = filteredEmployees, dark = isHomeDark)
                        }

                        // Search Bar Input
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { viewModel.setSearchQuery(it) },
                            placeholder = { Text(operationalText(appLanguage, "Search orders workers"), color = Color(0xFF6B4A7D), fontSize = 13.sp) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = "Search icon",
                                    tint = Color(0xFF6D28D9),
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(onClick = { viewModel.setSearchQuery("") }) {
                                        Icon(imageVector = Icons.Filled.Refresh, contentDescription = "Clear", tint = Color(0xFF5B3A75), modifier = Modifier.size(16.dp))
                                    }
                                }
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF6D28D9),
                                unfocusedBorderColor = Color(0xFFD8B4FE),
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedTextColor = Color(0xFF2E1065),
                                unfocusedTextColor = Color(0xFF2E1065),
                                cursorColor = Color(0xFF6D28D9)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )

                        // Overall Sales Order Summary Card List
                        Text(
                            "${operationalText(appLanguage, "Sales Orders Running").uppercase()} (${filteredOrders.size})",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6D28D9),
                            letterSpacing = 1.sp
                        )

                        if (filteredOrders.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White.copy(alpha = 0.64f), RoundedCornerShape(12.dp))
                                    .border(1.dp, Color(0xFFD8B4FE), RoundedCornerShape(12.dp))
                                    .padding(24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(operationalText(appLanguage, "No matching orders"), color = Color(0xFF5B3A75), fontSize = 13.sp)
                            }
                        } else {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                filteredOrders.forEach { order ->
                                    OverallSummaryCard(order = order, employees = employees, isDark = false) {
                                        selectedOrderDetail = order
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            operationalText(appLanguage, "Authorized offline terminal"),
                            fontSize = 11.sp,
                            color = Color(0xFF6B4A7D),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
    "Master" -> {
        MasterRegistryTab(
            innerPadding = innerPadding,
            viewModel = viewModel,
            salesOrders = salesOrders,
            employees = employees,
            onReleaseNewOrder = { showNewTaskDialog = true },
            onRegisterNewWorker = { 
                preselectedEmployee = null
                showClockInDialog = true 
            },
            onOrderSelect = { selectedOrderDetail = it },
            onManageEmployee = { emp ->
                preselectedEmployee = emp
                showClockInDialog = true
            },
            glassThemeStyle = glassThemeStyle
        )
    }
    "Supervisor App" -> {
        SupervisorTab(
            innerPadding = innerPadding,
            viewModel = viewModel,
            salesOrders = salesOrders,
            employees = employees,
            userEmail = userEmail,
            glassThemeStyle = glassThemeStyle
        )
    }
    "Report" -> {
        ReportTab(
            innerPadding = innerPadding,
            viewModel = viewModel,
            salesOrders = salesOrders,
            employees = employees,
            completedJobsCount = completedJobsCount,
            totalJobsCount = totalJobsCount,
            averageProductivity = averageProductivity,
            totalClockedHours = totalClockedHours,
            glassThemeStyle = glassThemeStyle
        )
    }
    }
}
        FloatingShopfloorBot(
            viewModel = viewModel,
            salesOrders = salesOrders,
            employees = employees,
            departments = departmentsList,
            categories = categoriesList,
            onOpenSupervisor = { activeTab = "Supervisor App" }
        )
        }
    }
}

    // Modal Dialog 1: Clock In / Out
    if (showClockInDialog) {
        var workerName by remember { mutableStateOf(preselectedEmployee?.name ?: "") }
        var activeTask by remember { mutableStateOf(preselectedEmployee?.task ?: "") }
        var workerDept by remember { mutableStateOf(preselectedEmployee?.department ?: departmentsList.firstOrNull()?.name.orEmpty()) }
        val isClockedOut = preselectedEmployee?.status == "Logged Out" || preselectedEmployee == null

        AlertDialog(
            onDismissRequest = { showClockInDialog = false },
            title = {
                Text(
                    text = if (isClockedOut) "Clock In New Worker" else "Manage Worker Station",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E1065)
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = workerName,
                        onValueChange = { if (isClockedOut) workerName = it },
                        label = { Text("Worker Full Name", color = Color(0xFF5B3A75)) },
                        enabled = isClockedOut,
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6D28D9),
                            unfocusedBorderColor = Color(0xFF6B4A7D),
                            focusedTextColor = Color(0xFF2E1065),
                            unfocusedTextColor = Color(0xFF2E1065)
                        )
                    )

                    // Department choices
                    ExposedDropdownSelection(
                        selectedValue = workerDept,
                        label = "Assign Station Department",
                        options = departmentsList.map { it.name }
                    ) {
                        workerDept = it
                    }

                    OutlinedTextField(
                        value = activeTask,
                        onValueChange = { activeTask = it },
                        label = { Text("Current Active Task", color = Color(0xFF5B3A75)) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6D28D9),
                            unfocusedBorderColor = Color(0xFF6B4A7D),
                            focusedTextColor = Color(0xFF2E1065),
                            unfocusedTextColor = Color(0xFF2E1065)
                        )
                    )
                }
            },
            confirmButton = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (!isClockedOut && preselectedEmployee != null) {
                        // Show "Clock Out" button
                        Button(
                            onClick = {
                                viewModel.clockOutEmployee(preselectedEmployee!!.name)
                                showClockInDialog = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB3261E)) // Sleek destructive
                        ) {
                            Text("Clock Out", fontWeight = FontWeight.Bold)
                        }
                    }

                    Button(
                        onClick = {
                            if (workerName.trim().isNotEmpty()) {
                                viewModel.clockInEmployee(workerName.trim(), workerDept, activeTask.trim().ifEmpty { "Assigned Duty" })
                                showClockInDialog = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6D28D9), // Sleek primary
                            contentColor = Color.White
                        ),
                        enabled = workerName.trim().isNotEmpty()
                    ) {
                        Text(if (isClockedOut) "Clock In" else "Update Duty", fontWeight = FontWeight.Bold)
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { showClockInDialog = false }) {
                    Text("Cancel", color = Color(0xFF6D28D9))
                }
            },
            containerColor = Color.White.copy(alpha = 0.72f) // SleekSurfaceVariant
        )
    }

    // Modal Dialog 2: Log Labour Units & Hours
    if (showLabourDialog) {
        val selectedOrderObj = preselectedOrder ?: salesOrders.firstOrNull { it.status != "Completed" } ?: salesOrders.firstOrNull()
        var selectedOrderId by remember { mutableStateOf(selectedOrderObj?.id ?: "") }
        
        val activeWorkers = employees.filter { it.status == "Active" }
        val firstWorker = preselectedEmployee ?: activeWorkers.firstOrNull()
        var selectedWorkerName by remember { mutableStateOf(firstWorker?.name ?: "") }
        
        var completedQtyDeltaInput by remember { mutableStateOf("") }
        var hoursDeltaInput by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showLabourDialog = false },
            title = {
                Text(
                    text = "Log Completed Labour Units",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E1065)
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Dropdown for Sales Orders
                    ExposedDropdownSelection(
                        selectedValue = selectedOrderId,
                        label = "Select Active Sales Order",
                        options = salesOrders.map { "${it.id} - ${it.item}" }
                    ) { selectedStr ->
                        selectedOrderId = selectedStr.split(" - ").first()
                    }

                    // Dropdown for Active Employees
                    ExposedDropdownSelection(
                        selectedValue = selectedWorkerName,
                        label = "Logging Operator",
                        options = employees.map { it.name }
                    ) {
                        selectedWorkerName = it
                    }

                    OutlinedTextField(
                        value = completedQtyDeltaInput,
                        onValueChange = { completedQtyDeltaInput = it },
                        label = { Text("Completed Qty Delta (units)", color = Color(0xFF5B3A75)) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6D28D9),
                            unfocusedBorderColor = Color(0xFF6B4A7D),
                            focusedTextColor = Color(0xFF2E1065),
                            unfocusedTextColor = Color(0xFF2E1065)
                        )
                    )

                    OutlinedTextField(
                        value = hoursDeltaInput,
                        onValueChange = { hoursDeltaInput = it },
                        label = { Text("Logged Duration (hours)", color = Color(0xFF5B3A75)) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6D28D9),
                            unfocusedBorderColor = Color(0xFF6B4A7D),
                            focusedTextColor = Color(0xFF2E1065),
                            unfocusedTextColor = Color(0xFF2E1065)
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val qDelta = completedQtyDeltaInput.toIntOrNull() ?: 0
                        val hDelta = hoursDeltaInput.toDoubleOrNull() ?: 0.0
                        if (selectedOrderId.isNotEmpty() && selectedWorkerName.isNotEmpty() && qDelta > 0 && hDelta > 0.0) {
                            viewModel.logLabour(selectedOrderId, qDelta, hDelta, selectedWorkerName)
                            showLabourDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6D28D9), // Sleek primary
                        contentColor = Color.White
                    ),
                    enabled = selectedOrderId.isNotEmpty() && selectedWorkerName.isNotEmpty()
                ) {
                    Text("Submit Labour Logs", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLabourDialog = false }) {
                    Text("Cancel", color = Color(0xFF6D28D9))
                }
            },
            containerColor = Color.White.copy(alpha = 0.72f) // SleekSurfaceVariant
        )
    }

    // Modal Dialog 3: Create New Task / Sales Order Job
    if (showNewTaskDialog) {
        var newIdInput by remember { mutableStateOf(generateNextSalesOrderId(salesOrders)) }
        var newItemInput by remember { mutableStateOf("") }
        var newTargetQtyInput by remember { mutableStateOf("") }
        var newTaskDept by remember { mutableStateOf(departmentsList.firstOrNull()?.name.orEmpty()) }

        AlertDialog(
            onDismissRequest = { showNewTaskDialog = false },
            title = {
                Text(
                    text = "Release New Production Run (Sales Order)",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E1065),
                    fontSize = 16.sp
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = newIdInput,
                        onValueChange = { newIdInput = it },
                        label = { Text("Sales Order Reference (ID)", color = Color(0xFF5B3A75)) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6D28D9),
                            unfocusedBorderColor = Color(0xFF6B4A7D),
                            focusedTextColor = Color(0xFF2E1065),
                            unfocusedTextColor = Color(0xFF2E1065)
                        )
                    )

                    OutlinedTextField(
                        value = newItemInput,
                        onValueChange = { newItemInput = it },
                        label = { Text("Manufactured Item Name", color = Color(0xFF5B3A75)) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6D28D9),
                            unfocusedBorderColor = Color(0xFF6B4A7D),
                            focusedTextColor = Color(0xFF2E1065),
                            unfocusedTextColor = Color(0xFF2E1065)
                        )
                    )

                    ExposedDropdownSelection(
                        selectedValue = newTaskDept,
                        label = "Target Production Department",
                        options = departmentsList.map { it.name }
                    ) {
                        newTaskDept = it
                    }

                    OutlinedTextField(
                        value = newTargetQtyInput,
                        onValueChange = { newTargetQtyInput = it },
                        label = { Text("Target Manufactured Units", color = Color(0xFF5B3A75)) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6D28D9),
                            unfocusedBorderColor = Color(0xFF6B4A7D),
                            focusedTextColor = Color(0xFF2E1065),
                            unfocusedTextColor = Color(0xFF2E1065)
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val target = newTargetQtyInput.toIntOrNull() ?: 100
                        if (newIdInput.trim().isNotEmpty() && newItemInput.trim().isNotEmpty() && target > 0) {
                            viewModel.addNewTask(newIdInput.trim().uppercase(), newItemInput.trim(), target, newTaskDept)
                            showNewTaskDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6D28D9), // Sleek primary
                        contentColor = Color.White
                    ),
                    enabled = newIdInput.trim().isNotEmpty() && newItemInput.trim().isNotEmpty()
                ) {
                    Text("Release Run", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showNewTaskDialog = false }) {
                    Text("Cancel", color = Color(0xFF6D28D9))
                }
            },
            containerColor = Color.White.copy(alpha = 0.72f) // SleekSurfaceVariant
        )
    }

    if (showRecycleBin) {
        val recycleScroll = rememberScrollState()
        AlertDialog(
            onDismissRequest = { showRecycleBin = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF59E0B).copy(alpha = 0.16f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.History, null, tint = Color(0xFFB45309), modifier = Modifier.size(24.dp))
                    }
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text(uiText(appLanguage, "recycleBin"), fontWeight = FontWeight.ExtraBold, color = Color(0xFF2E1065))
                        Text(
                            "${recoverableRecords.size} recoverable record(s)",
                            color = Color(0xFF6B4A7D),
                            fontSize = 11.sp
                        )
                    }
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 460.dp)
                        .verticalScroll(recycleScroll),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        color = Color(0xFFF8F5FF),
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.dp, Color(0xFFE9D5FF))
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text(
                                uiText(appLanguage, "retention").uppercase(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF6D28D9)
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "$recycleRetentionDays ${uiText(appLanguage, "days")}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF2E1065)
                            )
                            Text(
                                "Deleted records are automatically removed after this period.",
                                fontSize = 10.sp,
                                color = Color(0xFF6B4A7D)
                            )
                            Spacer(Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                listOf(7, 30, 60).forEach { days ->
                                    FilterChip(
                                        modifier = Modifier.weight(1f),
                                        selected = recycleRetentionDays == days,
                                        onClick = { viewModel.setRecycleRetentionDays(days) },
                                        label = { Text(days.toString(), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontSize = 10.sp, fontWeight = FontWeight.Bold) }
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                listOf(90, 365).forEach { days ->
                                    FilterChip(
                                        modifier = Modifier.weight(1f),
                                        selected = recycleRetentionDays == days,
                                        onClick = { viewModel.setRecycleRetentionDays(days) },
                                        label = {
                                            Text(
                                                if (days == 365) "365 · 1 year" else days.toString(),
                                                modifier = Modifier.fillMaxWidth(),
                                                textAlign = TextAlign.Center,
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Surface(
                        color = Color(0xFFFFFBF2),
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.dp, Color(0xFFFDE7B2))
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text(
                                uiText(appLanguage, "backupPath").uppercase(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFB45309)
                            )
                            Spacer(Modifier.height(5.dp))
                            Text(
                                backupLocationLabel,
                                color = Color(0xFF2E1065),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                "shopfloor-data.json",
                                color = Color(0xFF6B4A7D),
                                fontSize = 10.sp
                            )
                            Spacer(Modifier.height(8.dp))
                            OutlinedButton(
                                onClick = { backupFolderPicker.launch(null) },
                                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Filled.Folder, null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(7.dp))
                                Text(uiText(appLanguage, "changeBackupPath"), fontWeight = FontWeight.Bold, fontSize = 11.sp)
                            }
                            Spacer(Modifier.height(8.dp))
                            Text(
                                uiText(appLanguage, "protectedPath").uppercase(),
                                color = Color(0xFF6B4A7D),
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                viewModel.localBackupLocation(),
                                color = Color(0xFF6B4A7D),
                                fontSize = 9.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    if (recoverableRecords.isEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Filled.CheckCircle, null, tint = Color(0xFF16815D), modifier = Modifier.size(34.dp))
                            Spacer(Modifier.height(8.dp))
                            Text(
                                uiText(appLanguage, "emptyBin"),
                                color = Color(0xFF5B3A75),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    } else {
                        recoverableRecords.forEach { record ->
                            val payload = remember(record.archiveId) {
                                runCatching { org.json.JSONObject(record.payload) }.getOrNull()
                            }
                            val displayName = when (record.recordType) {
                                "salesOrders" -> payload?.optString("item")
                                "employees" -> payload?.optString("name")
                                "departments", "categories" -> payload?.optString("name")
                                "assignments" -> payload?.optString("employeeName")
                                "logs" -> payload?.optString("message")
                                else -> null
                            }.orEmpty().ifBlank { record.recordId }
                            val millisecondsLeft = (record.expiresAt - System.currentTimeMillis()).coerceAtLeast(0L)
                            val daysLeft = ((millisecondsLeft + 86_399_999L) / 86_400_000L).coerceAtLeast(1L)
                            Surface(
                                color = Color.White,
                                shape = RoundedCornerShape(14.dp),
                                border = BorderStroke(1.dp, Color(0xFFE9D5FF))
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(Modifier.weight(1f)) {
                                        Text(
                                            displayName,
                                            color = Color(0xFF2E1065),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            "${record.recordType} · $daysLeft ${uiText(appLanguage, "days")} left",
                                            color = Color(0xFF6B4A7D),
                                            fontSize = 9.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    TextButton(
                                        onClick = {
                                            viewModel.restoreDeletedRecord(record.archiveId)
                                            Toast.makeText(appContext, "${record.recordId} restored", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.heightIn(min = 48.dp)
                                    ) {
                                        Icon(Icons.Filled.Restore, null, modifier = Modifier.size(17.dp))
                                        Spacer(Modifier.width(4.dp))
                                        Text(uiText(appLanguage, "restore"), fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = { showRecycleBin = false }) {
                    Text(uiText(appLanguage, "done"), fontWeight = FontWeight.Bold)
                }
            },
            containerColor = Color.White
        )
    }

    // Confirmation Dialog before Logging Out
    if (showLogoutConfirmation) {
        AlertDialog(
            onDismissRequest = { showLogoutConfirmation = false },
            title = {
                Text(
                    text = uiText(appLanguage, "confirmLogout"),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E1065)
                )
            },
            text = {
                Text(
                    uiText(appLanguage, "logoutQuestion"),
                    color = Color(0xFF5B3A75)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutConfirmation = false
                        authProvider.logout()
                        onLogoutSuccess()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB3261E)) // Sleek destructive
                ) {
                    Text(uiText(appLanguage, "confirm"), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutConfirmation = false }) {
                    Text(uiText(appLanguage, "cancel"), color = Color(0xFF6D28D9))
                }
            },
            containerColor = Color.White.copy(alpha = 0.72f) // SleekSurfaceVariant
        )
    }
}

// Reusable KPI UI Widget Component
@Composable
fun KpiCard(
    title: String,
    value: String,
    sub: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.66f)),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFD8B4FE))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    title,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5B3A75),
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    value,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF2E1065)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    sub,
                    fontSize = 10.sp,
                    color = Color(0xFF6B4A7D)
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(tint.copy(alpha = 0.15f))
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = tint,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

// Sleek Dark KPI Card matching screenshot design
@Composable
fun SleekKpiCardDark(
    title: String,
    value: String,
    sub: String,
    subColor: Color = Color(0xFFFEF3C7),
    icon: ImageVector,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2E1065)),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFF6D28D9))
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Top Accent Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(accentColor)
            )
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFEF3C7),
                        letterSpacing = 0.5.sp
                    )
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = accentColor,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = value,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = sub,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = subColor
                )
            }
        }
    }
}

// Midnight analytics KPI card. The historical function name is retained for callers.
@Composable
fun SleekKpiCardLight(
    title: String,
    value: String,
    sub: String,
    subColor: Color = Color(0xFFA9B9D4),
    icon: ImageVector,
    accentColor: Color,
    dark: Boolean = false,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = if (dark) Color(0xCC071A35) else Color.White.copy(alpha = 0.66f)),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, if (dark) Color(0xFF1C4F92) else Color(0xFFD8B4FE))
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Top Accent Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.5.dp)
                    .background(accentColor)
            )
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title.uppercase(),
                        modifier = Modifier.weight(1f),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (dark) Color(0xFFAFC6E7) else Color(0xFF6B4A7D),
                        letterSpacing = 0.5.sp,
                        maxLines = 2,
                        minLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.width(6.dp))
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = accentColor,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = value,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (dark) Color(0xFFF7FBFF) else Color(0xFF2E1065)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = sub,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = subColor,
                    maxLines = 3,
                    minLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

// Actual vs Planned Cost by Sales Order, styled for Midnight Analytics.
@Composable
fun ActualVsPlannedCostCardLight(
    orders: List<SalesOrder>,
    employees: List<EmployeeActivity>,
    dark: Boolean = false,
    modifier: Modifier = Modifier
) {
    val surface = if (dark) Color(0xCC071A35) else Color(0xFFFCFAFF)
    val border = if (dark) Color(0xFF1C4F92) else Color(0xFFE3D4FA)
    val chartStart = if (dark) Color(0xFF0D2B57) else Color(0xFFF5F0FF)
    val chartEnd = if (dark) Color(0xFF081B36) else Color(0xFFFCFAFF)
    val title = if (dark) Color(0xFFF7FBFF) else Color(0xFF2E1065)
    val muted = if (dark) Color(0xFFAFC6E7) else Color(0xFF67557A)
    val grid = if (dark) Color(0xFF1C4F92) else Color(0xFFE2D7F3)
    val plannedColor = if (dark) Color(0xFF4774C8) else Color(0xFF9DB5E8)
    val actualColor = if (dark) Color(0xFF24B9FF) else Color(0xFF6D28D9)
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = surface),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, border),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(40.dp).clip(RoundedCornerShape(12.dp)).background(Brush.linearGradient(listOf(Color(0xFF6D28D9), Color(0xFFA855F7)))), contentAlignment = Alignment.Center) {
                    Icon(Icons.Filled.ShowChart, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
                }
                Spacer(Modifier.width(10.dp))
                Column(Modifier.weight(1f)) {
                    Text(operationalText(currentOperationalLanguage(), "Actual vs. Planned Cost by Sales Order"), color = title, fontWeight = FontWeight.Bold, fontSize = 15.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
                    Text(operationalText(currentOperationalLanguage(), "Values in INR (₹)"), color = muted, fontSize = 10.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            val displayOrders = orders
            val responsiveChartWidth = maxOf(316, displayOrders.size * 72).dp
            val chartMax = displayOrders.maxOfOrNull {
                maxOf(getPlannedCostForOrder(it), getActualCostForOrder(it, employees))
            }?.coerceAtLeast(1.0)?.toFloat() ?: 1f

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(156.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Brush.verticalGradient(listOf(chartStart, chartEnd)))
                    .horizontalScroll(rememberScrollState())
            ) {
                Canvas(
                    modifier = Modifier.width(responsiveChartWidth).fillMaxHeight()
                        .padding(horizontal = 6.dp, vertical = 6.dp)
                ) {
                    val w = size.width
                    val h = size.height
                    val bottomMargin = 24.dp.toPx()
                    val leftMargin = 38.dp.toPx()
                    val chartW = w - leftMargin
                    val chartH = h - bottomMargin

                    // Draw Y Grid lines
                    val maxVal = chartMax
                    val gridSteps = 4
                    for (i in 0..gridSteps) {
                        val y = (chartH / gridSteps) * i
                        drawLine(
                            color = grid,
                            start = Offset(leftMargin, y),
                            end = Offset(w, y),
                            strokeWidth = 1f
                        )
                        val valText = formatCompactChartCurrency(((gridSteps - i).toDouble() / gridSteps) * maxVal)
                        drawIntoCanvas { canvas ->
                            val paint = android.graphics.Paint().apply {
                                color = muted.toArgb()
                                textSize = 8.dp.toPx()
                                textAlign = android.graphics.Paint.Align.RIGHT
                            }
                            canvas.nativeCanvas.drawText(valText, leftMargin - 6.dp.toPx(), y + 3.dp.toPx(), paint)
                        }
                    }

                    // Draw Bars for each order
                    val itemCount = displayOrders.size
                    val slotW = if (itemCount > 0) chartW / itemCount else chartW
                    val barWidth = slotW * 0.29f

                    displayOrders.forEachIndexed { index, ord ->
                        val planned = getPlannedCostForOrder(ord).toFloat()
                        val actual = getActualCostForOrder(ord, employees).toFloat()

                        val plannedH = if (planned > 0f) (planned / maxVal).coerceIn(0f, 1f) * chartH else 0f
                        val actualH = if (actual > 0f) (actual / maxVal).coerceIn(0f, 1f) * chartH else 0f

                        val centerX = leftMargin + index * slotW + slotW / 2
                        val pStartX = centerX - barWidth - 2.dp.toPx()
                        val aStartX = centerX + 2.dp.toPx()

                        // Planned Bar
                        drawRoundRect(
                            brush = Brush.verticalGradient(listOf(plannedColor, plannedColor.copy(alpha = .45f)), chartH - plannedH, chartH),
                            topLeft = Offset(pStartX, chartH - plannedH),
                            size = Size(barWidth, plannedH),
                            cornerRadius = CornerRadius(3.dp.toPx(), 3.dp.toPx())
                        )

                        // Actual Bar (Sleek Purple)
                        if (actualH > 0) {
                            drawRoundRect(
                                brush = Brush.verticalGradient(listOf(actualColor, if (dark) Color(0xFF2563EB) else Color(0xFFA855F7)), chartH - actualH, chartH),
                                topLeft = Offset(aStartX, chartH - actualH),
                                size = Size(barWidth, actualH),
                                cornerRadius = CornerRadius(3.dp.toPx(), 3.dp.toPx())
                            )
                        }

                        // X-axis Label
                        val labelId = ord.id
                        drawIntoCanvas { canvas ->
                            val paint = android.graphics.Paint().apply {
                                color = muted.toArgb()
                                textSize = 8.dp.toPx()
                                textAlign = android.graphics.Paint.Align.CENTER
                            }
                            canvas.nativeCanvas.drawText(labelId, centerX, h - 8.dp.toPx(), paint)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Legend
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(width = 18.dp, height = 6.dp).background(plannedColor, RoundedCornerShape(50)))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(operationalText(currentOperationalLanguage(), "Planned Cost"), color = muted, fontSize = 11.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(width = 18.dp, height = 6.dp).background(actualColor, RoundedCornerShape(50)))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(operationalText(currentOperationalLanguage(), "Actual Cost"), color = muted, fontSize = 11.sp)
                }
            }
        }
    }
}

// Cost by Department Donut Chart Card, styled for Midnight Analytics.
@Composable
fun CostByDepartmentCardLight(
    orders: List<SalesOrder>,
    employees: List<EmployeeActivity>,
    selectedDept: String,
    dark: Boolean = false,
    modifier: Modifier = Modifier
) {
    val surface = if (dark) Color(0xCC071A35) else Color(0xFFFCFAFF)
    val border = if (dark) Color(0xFF1C4F92) else Color(0xFFE3D4FA)
    val chartStart = if (dark) Color(0xFF0D2B57) else Color(0xFFF4EFFF)
    val chartEnd = if (dark) Color(0xFF082642) else Color(0xFFF0FDF8)
    val title = if (dark) Color(0xFFF7FBFF) else Color(0xFF2E1065)
    val muted = if (dark) Color(0xFFAFC6E7) else Color(0xFF67557A)
    val emptyDonut = if (dark) Color(0xFF1C4F92) else Color(0xFFE9E0F7)
    val colors = if (dark) listOf(Color(0xFF24B9FF), Color(0xFF2563EB), Color(0xFF7C3AED), Color(0xFF34D399))
        else listOf(Color(0xFF6D28D9), Color(0xFF16815D), Color(0xFFF59E0B), Color(0xFFA855F7))
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = surface),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, border),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(40.dp).clip(RoundedCornerShape(12.dp)).background(Brush.linearGradient(listOf(Color(0xFF16815D), Color(0xFF34D399)))), contentAlignment = Alignment.Center) {
                    Icon(Icons.Filled.AttachMoney, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
                }
                Spacer(Modifier.width(10.dp))
                Column {
                    Text(operationalText(currentOperationalLanguage(), "Cost by Department"), color = title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text("Live actual labour cost distribution", color = muted, fontSize = 10.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            val deptData = orders
                .groupBy { it.department }
                .mapValues { (_, departmentOrders) ->
                    departmentOrders.sumOf { order -> getActualCostForOrder(order, employees) }
                }
                .filterValues { it > 0.0 }
                .entries
                .mapIndexed { index, entry ->
                    (entry.key to entry.value.toFloat()) to colors[index % colors.size]
                }

            val totalVal = deptData.sumOf { it.first.second.toDouble() }.toFloat()

            Row(
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp))
                    .background(Brush.horizontalGradient(listOf(chartStart, chartEnd)))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(116.dp), contentAlignment = Alignment.Center) {
                Canvas(modifier = Modifier.size(104.dp)) {
                    val strokeW = 18.dp.toPx()
                    var startAngle = -90f

                    drawArc(emptyDonut, -90f, 360f, false, style = Stroke(width = strokeW, cap = StrokeCap.Round))
                    if (totalVal > 0f) {
                        deptData.forEach { (pair, color) ->
                            val (_, valFactor) = pair
                            val sweep = (valFactor / totalVal) * 360f
                            drawArc(color, startAngle, (sweep - 3f).coerceAtLeast(1f), false, style = Stroke(width = strokeW, cap = StrokeCap.Round))
                            startAngle += sweep
                        }
                    }
                }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(if (totalVal > 0f) formatCostValue(totalVal.toDouble()) else "₹0", color = title, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, maxLines = 1)
                        Text(operationalText(currentOperationalLanguage(), "Total").uppercase(), color = muted, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (deptData.isEmpty()) {
                        Text("No department cost yet", color = muted, fontSize = 12.sp)
                    } else deptData.forEach { (pair, color) ->
                        val (deptName, value) = pair
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(Modifier.size(8.dp).clip(CircleShape).background(color))
                            Spacer(Modifier.width(7.dp))
                            Text(deptName, color = muted, fontSize = 10.sp, fontWeight = if (selectedDept == deptName) FontWeight.Bold else FontWeight.Medium, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f))
                            Text("${((value / totalVal) * 100).roundToInt()}%", color = title, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

private fun formatChartHours(hours: Double): String =
    if (hours > 0.0 && hours < 0.1) String.format(Locale.US, "%.2f", hours)
    else String.format(Locale.US, "%.1f", hours)

private fun formatCompactChartCurrency(value: Double): String = when {
    value >= 1_000_000.0 -> "\u20B9${String.format(Locale.US, "%.1f", value / 1_000_000.0)}M"
    value >= 1_000.0 -> "\u20B9${String.format(Locale.US, "%.0f", value / 1_000.0)}K"
    else -> "\u20B9${String.format(Locale.US, "%.0f", value)}"
}

@Composable
private fun LabourDeploymentChartCard(
    employees: List<EmployeeActivity>,
    dark: Boolean,
    modifier: Modifier = Modifier
) {
    val data = employees.filter { it.hourlyRate > 0.0 }.takeLast(6)
    val rawMaxRate = data.maxOfOrNull { it.hourlyRate } ?: 0.0
    val maxRate = when {
        rawMaxRate <= 0.0 -> 1.0
        rawMaxRate < 10.0 -> rawMaxRate * 1.25
        rawMaxRate < 100.0 -> rawMaxRate * 1.18
        else -> rawMaxRate * 1.12
    }
    val averageRate = data.map { it.hourlyRate }.average().takeIf { !it.isNaN() } ?: 0.0
    val surface = if (dark) Color(0xFF09172F) else Color(0xFFFCFAFF)
    val chartSurface = if (dark) Color(0xFF0D1E3A) else Color(0xFFF7F2FF)
    val primary = if (dark) Color(0xFFB892FF) else Color(0xFF6D28D9)
    val secondary = if (dark) Color(0xFF34D399) else Color(0xFF16815D)
    val titleColor = if (dark) Color.White else Color(0xFF2E1065)
    val muted = if (dark) Color(0xFFD8C9F2) else Color(0xFF67557A)
    val description = if (data.isEmpty()) "No employee hourly rates available" else
        data.joinToString { "${it.empId.ifBlank { it.name }} ${formatCostValue(it.hourlyRate)} per hour" }

    Card(
        modifier = modifier.fillMaxWidth().semantics { contentDescription = "Employee hourly rate chart. $description" },
        colors = CardDefaults.cardColors(containerColor = surface),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, if (dark) Color(0xFF5B3589) else Color(0xFFE3D4FA)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(Modifier.padding(14.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier.size(38.dp).clip(RoundedCornerShape(11.dp)).background(Brush.linearGradient(listOf(primary, Color(0xFFA855F7)))), contentAlignment = Alignment.Center) {
                        Icon(Icons.Filled.ShowChart, null, tint = Color.White, modifier = Modifier.size(21.dp))
                    }
                    Spacer(Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Hourly Rate (\u20B9/hr)", color = titleColor, fontWeight = FontWeight.Bold, fontSize = 15.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
                        Text("Employee rate comparison \u00B7 last 6 employees", color = muted, fontSize = 10.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
                    }
                }
                Spacer(Modifier.width(8.dp))
                Column(horizontalAlignment = Alignment.End) {
                    Text("${formatCostValue(averageRate)}/hr", color = secondary, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                    Text("AVERAGE", color = muted, fontWeight = FontWeight.Bold, fontSize = 9.sp)
                }
            }
            Spacer(Modifier.height(10.dp))
            if (data.isEmpty()) {
                Box(Modifier.fillMaxWidth().height(96.dp).clip(RoundedCornerShape(14.dp)).background(chartSurface), contentAlignment = Alignment.Center) {
                    Text("No hourly rates yet", color = muted, fontSize = 12.sp)
                }
            } else Box(
                Modifier.fillMaxWidth().height(190.dp).clip(RoundedCornerShape(16.dp))
                    .background(Brush.verticalGradient(listOf(Color(0xFFF4ECFF), Color(0xFFFBF9FF))))
                    .border(1.dp, Color(0xFFD8B4FE), RoundedCornerShape(16.dp))
            ) {
                Canvas(Modifier.fillMaxSize().padding(horizontal = 8.dp, vertical = 6.dp)) {
                    val left = 34.dp.toPx(); val right = 10.dp.toPx(); val top = 26.dp.toPx(); val bottom = 28.dp.toPx()
                    val cw = size.width - left - right; val ch = size.height - top - bottom
                    repeat(4) { index ->
                        val y = top + ch * index / 3f
                        drawLine(Color(0xFFD7C8ED), Offset(left, y), Offset(left + cw, y), 1.dp.toPx(), pathEffect = PathEffect.dashPathEffect(floatArrayOf(8.dp.toPx(), 7.dp.toPx())))
                        drawIntoCanvas { canvas ->
                            val p = android.graphics.Paint().apply { color = Color(0xFF67557A).toArgb(); textSize = 8.dp.toPx(); textAlign = android.graphics.Paint.Align.RIGHT }
                            canvas.nativeCanvas.drawText(formatCostValue(maxRate * (3 - index) / 3), left - 6.dp.toPx(), y + 3.dp.toPx(), p)
                        }
                    }

                    val step = if (data.size > 1) cw / (data.size - 1) else 0f
                    fun x(i: Int) = if (data.size == 1) left + cw else left + i * step
                    fun y(rate: Double) = top + ch - (rate / maxRate * ch).toFloat()
                    val points = if (data.size == 1) {
                        val current = data.first().hourlyRate
                        listOf(
                            Offset(left, y(current)),
                            Offset(left + cw * .34f, y(current)),
                            Offset(left + cw * .68f, y(current)),
                            Offset(left + cw, y(current))
                        )
                    } else data.mapIndexed { index, employee -> Offset(x(index), y(employee.hourlyRate)) }
                    fun smoothPath(closeArea: Boolean): Path = Path().apply {
                        if (closeArea) {
                            moveTo(points.first().x, top + ch)
                            lineTo(points.first().x, points.first().y)
                        } else {
                            moveTo(points.first().x, points.first().y)
                        }
                        if (points.size > 1) {
                            for (index in 0 until points.lastIndex) {
                                val p0 = points[(index - 1).coerceAtLeast(0)]
                                val p1 = points[index]
                                val p2 = points[index + 1]
                                val p3 = points[(index + 2).coerceAtMost(points.lastIndex)]
                                cubicTo(
                                    p1.x + (p2.x - p0.x) / 6f,
                                    p1.y + (p2.y - p0.y) / 6f,
                                    p2.x - (p3.x - p1.x) / 6f,
                                    p2.y - (p3.y - p1.y) / 6f,
                                    p2.x,
                                    p2.y
                                )
                            }
                        }
                        if (closeArea) { lineTo(points.last().x, top + ch); close() }
                    }
                    val latest = points.last()
                    drawLine(Color(0xFF9F85C5), Offset(latest.x, top), Offset(latest.x, top + ch), 1.dp.toPx(), pathEffect = PathEffect.dashPathEffect(floatArrayOf(5.dp.toPx(), 5.dp.toPx())))
                    drawPath(smoothPath(true), Brush.verticalGradient(listOf(Color(0xFFA855F7).copy(alpha = .34f), Color(0xFF6D28D9).copy(alpha = .08f), Color.Transparent), top, top + ch))
                    drawPath(smoothPath(false), Color(0xFFA855F7).copy(alpha = .18f), style = Stroke(width = 11.dp.toPx(), cap = StrokeCap.Round))
                    drawPath(smoothPath(false), Brush.horizontalGradient(listOf(Color(0xFF6D28D9), Color(0xFFA855F7), Color(0xFF7C3AED)), left, left + cw), style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round))
                    points.forEachIndexed { index, point ->
                        if (data.size == 1 && index != points.lastIndex) return@forEachIndexed
                        drawCircle(Color(0xFFA855F7).copy(alpha = if (index == points.lastIndex) .24f else .12f), if (index == points.lastIndex) 12.dp.toPx() else 7.dp.toPx(), point)
                        drawCircle(Color.White, 6.dp.toPx(), point)
                        drawCircle(if (index == points.lastIndex) Color(0xFF7C3AED) else Color(0xFFA855F7), 4.dp.toPx(), point)
                        drawIntoCanvas { canvas ->
                            val labelPaint = android.graphics.Paint().apply { color = Color(0xFF5B3A75).toArgb(); textSize = 8.dp.toPx(); textAlign = android.graphics.Paint.Align.CENTER }
                            val source = if (data.size == 1) data.first() else data[index]
                            val id = source.empId.ifBlank { source.name }.let { if (it.length > 8) it.takeLast(8) else it }
                            canvas.nativeCanvas.drawText(id, point.x, size.height - 5.dp.toPx(), labelPaint)
                        }
                    }
                }
                Surface(
                    modifier = Modifier.align(Alignment.TopEnd).padding(10.dp),
                    color = Color(0xFFF3E8FF),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Color(0xFFD8B4FE))
                ) {
                    Row(Modifier.padding(horizontal = 10.dp, vertical = 7.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(7.dp).clip(CircleShape).background(Color(0xFF7C3AED)))
                        Spacer(Modifier.width(6.dp))
                        Text(data.last().empId.ifBlank { data.last().name }, color = Color(0xFF67557A), fontSize = 9.sp, maxLines = 1)
                        Spacer(Modifier.width(7.dp))
                        Text("${formatCostValue(data.last().hourlyRate)}/hr", color = Color(0xFF2E1065), fontWeight = FontWeight.ExtraBold, fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun LabourCategoryChartCard(
    employees: List<EmployeeActivity>,
    dark: Boolean,
    modifier: Modifier = Modifier
) {
    val data = employees.filter { it.hoursClocked > 0.0 }
        .groupBy { it.category.ifBlank { "Unassigned" } }
        .mapValues { (_, people) -> people.sumOf { it.hoursClocked } }
        .toList().sortedByDescending { it.second }
    val maxHours = data.maxOfOrNull { it.second }?.coerceAtLeast(1.0) ?: 1.0
    val surface = if (dark) Color(0xFF241047) else Color(0xFFFCFAFF)
    val chartSurface = if (dark) Color(0xFF32165C) else Color(0xFFF7F2FF)
    val titleColor = if (dark) Color.White else Color(0xFF2E1065)
    val muted = if (dark) Color(0xFFA9B9D4) else Color(0xFF67557A)
    val grid = if (dark) Color(0xFF29466E) else Color(0xFFE1D5F3)
    val colors = if (dark) listOf(Color(0xFF7357FF), Color(0xFF22D3EE), Color(0xFFFFB547), Color(0xFF438BFF), Color(0xFFFF77B7))
        else listOf(Color(0xFF6D28D9), Color(0xFF16815D), Color(0xFFB45309), Color(0xFF2563EB), Color(0xFFBE185D))
    val description = if (data.isEmpty()) "No category hours available" else data.joinToString { "${it.first} ${formatChartHours(it.second)} hours" }

    Card(
        modifier = modifier.fillMaxWidth().semantics { contentDescription = "Manhours by labour category. $description" },
        colors = CardDefaults.cardColors(containerColor = surface), shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, if (dark) Color(0xFF23395D) else Color(0xFFE3D4FA)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(38.dp).clip(RoundedCornerShape(11.dp)).background(Brush.linearGradient(listOf(colors[0], colors[1]))), contentAlignment = Alignment.Center) {
                    Icon(Icons.Filled.TableChart, null, tint = Color.White, modifier = Modifier.size(21.dp))
                }
                Spacer(Modifier.width(10.dp))
                Column {
                    Text(operationalText(currentOperationalLanguage(), "Manhours by Category"), color = titleColor, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text(operationalText(currentOperationalLanguage(), "Ranked by total consumed hours"), color = muted, fontSize = 10.sp)
                }
            }
            Spacer(Modifier.height(10.dp))
            Box(Modifier.fillMaxWidth().height(if (data.isEmpty()) 112.dp else (data.size * 40 + 28).dp).clip(RoundedCornerShape(14.dp)).background(Brush.horizontalGradient(listOf(chartSurface, surface)))) {
                Canvas(Modifier.fillMaxSize().padding(horizontal = 8.dp, vertical = 6.dp)) {
                    val left = 86.dp.toPx(); val right = 42.dp.toPx(); val top = 8.dp.toPx(); val bottom = 20.dp.toPx()
                    val cw = size.width - left - right; val ch = size.height - top - bottom
                    repeat(4) { index ->
                        val x = left + cw * index / 3f
                        drawLine(grid, Offset(x, top), Offset(x, top + ch), 1.dp.toPx())
                        drawIntoCanvas { canvas ->
                            val p = android.graphics.Paint().apply { color = muted.toArgb(); textSize = 8.dp.toPx(); textAlign = android.graphics.Paint.Align.CENTER }
                            canvas.nativeCanvas.drawText(String.format(Locale.US, "%.1f", maxHours * index / 3), x, size.height - 2.dp.toPx(), p)
                        }
                    }
                    if (data.isNotEmpty()) {
                        val slot = ch / data.size
                        data.forEachIndexed { index, (category, hours) ->
                            val barH = (slot * .58f).coerceAtMost(22.dp.toPx()); val y = top + index * slot + (slot - barH) / 2
                            val barW = (hours / maxHours * cw).toFloat()
                            drawRoundRect(colors[index].copy(alpha = .12f), Offset(left, y), Size(cw, barH), CornerRadius(barH / 2))
                            drawRoundRect(Brush.horizontalGradient(listOf(colors[index].copy(alpha = .75f), colors[index])), Offset(left, y), Size(barW, barH), CornerRadius(barH / 2))
                            drawCircle(colors[index], 3.dp.toPx(), Offset(left - 78.dp.toPx(), y + barH / 2))
                            drawIntoCanvas { canvas ->
                                val labelPaint = android.graphics.Paint().apply { color = titleColor.toArgb(); textSize = 9.dp.toPx(); textAlign = android.graphics.Paint.Align.RIGHT }
                                val valuePaint = android.graphics.Paint().apply { color = titleColor.toArgb(); textSize = 9.dp.toPx(); textAlign = android.graphics.Paint.Align.LEFT; isFakeBoldText = true }
                                canvas.nativeCanvas.drawText(category.let { if (it.length > 12) it.take(11) + "…" else it }, left - 7.dp.toPx(), y + barH * .68f, labelPaint)
                                canvas.nativeCanvas.drawText("${formatChartHours(hours)} h", left + barW + 5.dp.toPx(), y + barH * .68f, valuePaint)
                            }
                        }
                    }
                }
                if (data.isEmpty()) Text(operationalText(currentOperationalLanguage(), "No category hours yet"), color = muted, fontSize = 12.sp, modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

private data class SalesOrderHoursPoint(
    val order: SalesOrder,
    val actualHours: Double,
    val actualCost: Double
)

@Composable
private fun SalesOrderActualHoursChartCard(
    orders: List<SalesOrder>,
    employees: List<EmployeeActivity>,
    dark: Boolean,
    modifier: Modifier = Modifier
) {
    val surface = if (dark) Color(0xCC071A35) else Color(0xFFFCFAFF)
    val chartSurface = if (dark) Color(0xFF081B36) else Color(0xFFFBF9FF)
    val titleColor = if (dark) Color(0xFFF7FBFF) else Color(0xFF2E1065)
    val muted = if (dark) Color(0xFFAFC6E7) else Color(0xFF67557A)
    val grid = if (dark) Color(0xFF1C4F92) else Color(0xFFD7C8ED)
    val violet = if (dark) Color(0xFF2563EB) else Color(0xFF6D28D9)
    val cyan = if (dark) Color(0xFF24B9FF) else Color(0xFFA855F7)
    val data = orders.map { order ->
        SalesOrderHoursPoint(
            order = order,
            actualHours = getActualHrForOrder(order, employees),
            actualCost = getActualCostForOrder(order, employees)
        )
    }
    val rawMaxHours = data.maxOfOrNull { it.actualHours } ?: 0.0
    val tickStep = when {
        rawMaxHours <= 4.0 -> 1.0
        rawMaxHours <= 10.0 -> 2.0
        rawMaxHours <= 25.0 -> 5.0
        rawMaxHours <= 50.0 -> 10.0
        else -> ceil(rawMaxHours / 40.0) * 10.0
    }
    val axisMax = maxOf(tickStep * 4.0, ceil(rawMaxHours / tickStep) * tickStep)
    val totalActualHours = data.sumOf { it.actualHours }
    var selectedOrderId by rememberSaveable { mutableStateOf<String?>(null) }

    LaunchedEffect(data.map { it.order.id }) {
        if (data.none { it.order.id == selectedOrderId }) {
            selectedOrderId = data.lastOrNull()?.order?.id
        }
    }

    Card(
        modifier = modifier.fillMaxWidth().semantics {
            contentDescription = "Sales order actual-hours chart. " + data.joinToString { point ->
                "${point.order.id}, ${formatChartHours(point.actualHours)} hours, ${formatCostValue(point.actualCost)} cost"
            }
        },
        colors = CardDefaults.cardColors(containerColor = surface),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, if (dark) Color(0xFF1C4F92) else Color(0xFFE3D4FA)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier.size(40.dp).clip(RoundedCornerShape(12.dp))
                        .background(Brush.linearGradient(listOf(violet, cyan))),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.ShowChart, null, tint = Color.White, modifier = Modifier.size(22.dp))
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text("Sales Order Actual Hours", color = titleColor, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Live hours by sales order \u00B7 tap a point", color = muted, fontSize = 11.sp)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("${formatChartHours(totalActualHours)} h", color = cyan, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                    Text("TOTAL", color = muted, fontWeight = FontWeight.Bold, fontSize = 9.sp)
                }
            }
            Spacer(Modifier.height(12.dp))

            if (data.isEmpty()) {
                Box(
                    Modifier.fillMaxWidth().height(112.dp).clip(RoundedCornerShape(16.dp))
                        .background(chartSurface),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No sales orders yet", color = muted, fontSize = 12.sp)
                }
            } else {
                val chartWidth = maxOf(316, data.size * 72).dp
                Box(
                    Modifier.fillMaxWidth().height(224.dp).clip(RoundedCornerShape(16.dp))
                        .background(Brush.verticalGradient(if (dark) listOf(Color(0xFF0D2B57), chartSurface) else listOf(Color(0xFFF4ECFF), chartSurface)))
                        .border(1.dp, grid, RoundedCornerShape(16.dp))
                        .horizontalScroll(rememberScrollState())
                ) {
                    Canvas(
                        Modifier.width(chartWidth).fillMaxHeight().padding(horizontal = 8.dp, vertical = 6.dp)
                            .pointerInput(data.map { it.order.id }) {
                                detectTapGestures { tap ->
                                    val left = 52.dp.toPx()
                                    val right = 14.dp.toPx()
                                    val plotWidth = size.width - left - right
                                    val index = if (data.size == 1) 0 else
                                        (((tap.x - left) / plotWidth) * (data.size - 1)).roundToInt().coerceIn(data.indices)
                                    val pointX = if (data.size == 1) left + plotWidth / 2f
                                    else left + plotWidth * index / (data.size - 1)
                                    if (kotlin.math.abs(tap.x - pointX) <= 32.dp.toPx()) {
                                        selectedOrderId = data[index].order.id
                                    }
                                }
                            }
                    ) {
                        val left = 52.dp.toPx()
                        val right = 14.dp.toPx()
                        val top = 42.dp.toPx()
                        val bottom = 34.dp.toPx()
                        val chartW = size.width - left - right
                        val chartH = size.height - top - bottom
                        val labelColor = muted
                        val gridColor = grid

                        repeat(5) { index ->
                            val lineY = top + chartH * index / 4f
                            val tickValue = axisMax * (4 - index) / 4.0
                            drawLine(
                                gridColor,
                                Offset(left, lineY),
                                Offset(left + chartW, lineY),
                                1.dp.toPx(),
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(7.dp.toPx(), 6.dp.toPx()))
                            )
                            drawIntoCanvas { canvas ->
                                val paint = android.graphics.Paint().apply {
                                    color = labelColor.toArgb()
                                    textSize = 8.dp.toPx()
                                    textAlign = android.graphics.Paint.Align.RIGHT
                                }
                                val label = if (tickValue % 1.0 == 0.0) "${tickValue.toInt()} hr" else String.format(Locale.US, "%.1f hr", tickValue)
                                canvas.nativeCanvas.drawText(label, left - 7.dp.toPx(), lineY + 3.dp.toPx(), paint)
                            }
                        }

                        fun pointX(index: Int): Float = if (data.size == 1) left + chartW / 2f
                        else left + chartW * index / (data.size - 1)
                        fun pointY(hours: Double): Float = top + chartH - (hours.coerceIn(0.0, axisMax) / axisMax * chartH).toFloat()
                        val points = data.mapIndexed { index, point -> Offset(pointX(index), pointY(point.actualHours)) }

                        if (points.size > 1) {
                            val linePath = Path().apply {
                                moveTo(points.first().x, points.first().y)
                                for (index in 0 until points.lastIndex) {
                                    val p0 = points[(index - 1).coerceAtLeast(0)]
                                    val p1 = points[index]
                                    val p2 = points[index + 1]
                                    val p3 = points[(index + 2).coerceAtMost(points.lastIndex)]
                                    cubicTo(
                                        p1.x + (p2.x - p0.x) / 6f,
                                        p1.y + (p2.y - p0.y) / 6f,
                                        p2.x - (p3.x - p1.x) / 6f,
                                        p2.y - (p3.y - p1.y) / 6f,
                                        p2.x,
                                        p2.y
                                    )
                                }
                            }
                            val areaPath = Path().apply {
                                addPath(linePath)
                                lineTo(points.last().x, top + chartH)
                                lineTo(points.first().x, top + chartH)
                                close()
                            }
                            drawPath(
                                areaPath,
                                Brush.verticalGradient(
                                    listOf(cyan.copy(alpha = .32f), violet.copy(alpha = .11f), Color.Transparent),
                                    top,
                                    top + chartH
                                )
                            )
                            drawPath(linePath, cyan.copy(alpha = .18f), style = Stroke(10.dp.toPx(), cap = StrokeCap.Round))
                            drawPath(
                                linePath,
                                Brush.horizontalGradient(if (dark) listOf(violet, Color(0xFF438BFF), cyan) else listOf(violet, Color(0xFFA855F7), Color(0xFF7C3AED)), left, left + chartW),
                                style = Stroke(4.dp.toPx(), cap = StrokeCap.Round)
                            )
                        }

                        points.forEachIndexed { index, point ->
                            val selected = data[index].order.id == selectedOrderId
                            drawCircle(cyan.copy(alpha = if (selected) .30f else .14f), if (selected) 13.dp.toPx() else 9.dp.toPx(), point)
                            drawCircle(if (dark) Color(0xFFF7FBFF) else Color.White, 6.dp.toPx(), point)
                            drawCircle(if (selected) cyan else violet, 4.dp.toPx(), point)
                            drawIntoCanvas { canvas ->
                                val paint = android.graphics.Paint().apply {
                                    color = muted.toArgb()
                                    textSize = 8.dp.toPx()
                                    textAlign = android.graphics.Paint.Align.CENTER
                                    isFakeBoldText = selected
                                }
                                canvas.nativeCanvas.drawText(data[index].order.id, point.x, size.height - 8.dp.toPx(), paint)
                            }
                        }

                        val selectedIndex = data.indexOfFirst { it.order.id == selectedOrderId }
                        if (selectedIndex >= 0) {
                            val selected = data[selectedIndex]
                            val anchor = points[selectedIndex]
                            val tooltipWidth = 132.dp.toPx()
                            val tooltipHeight = 52.dp.toPx()
                            val tooltipX = (anchor.x - tooltipWidth / 2f).coerceIn(left, size.width - tooltipWidth - 6.dp.toPx())
                            val tooltipY = (anchor.y - tooltipHeight - 12.dp.toPx()).let { proposed ->
                                if (proposed < 4.dp.toPx()) anchor.y + 12.dp.toPx() else proposed
                            }
                            drawRoundRect(
                                if (dark) Color(0xFF06172F) else Color(0xFFFFFFFF),
                                Offset(tooltipX, tooltipY),
                                Size(tooltipWidth, tooltipHeight),
                                CornerRadius(10.dp.toPx())
                            )
                            drawRoundRect(
                                if (dark) Color(0xFF1C75C7) else Color(0xFFD8B4FE),
                                Offset(tooltipX, tooltipY),
                                Size(tooltipWidth, tooltipHeight),
                                CornerRadius(10.dp.toPx()),
                                style = Stroke(1.dp.toPx())
                            )
                            drawIntoCanvas { canvas ->
                                val titlePaint = android.graphics.Paint().apply {
                                    color = titleColor.toArgb(); textSize = 9.dp.toPx(); isFakeBoldText = true
                                }
                                val detailPaint = android.graphics.Paint().apply {
                                    color = muted.toArgb(); textSize = 8.dp.toPx()
                                }
                                canvas.nativeCanvas.drawText(selected.order.id, tooltipX + 10.dp.toPx(), tooltipY + 14.dp.toPx(), titlePaint)
                                canvas.nativeCanvas.drawText("Actual: ${formatChartHours(selected.actualHours)} hrs", tooltipX + 10.dp.toPx(), tooltipY + 29.dp.toPx(), detailPaint)
                                canvas.nativeCanvas.drawText("Cost: ${formatCostValue(selected.actualCost)}", tooltipX + 10.dp.toPx(), tooltipY + 43.dp.toPx(), detailPaint)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DailyLabourDeploymentCardLight(
    orders: List<SalesOrder>,
    employees: List<EmployeeActivity>,
    dark: Boolean = false,
    modifier: Modifier = Modifier
) = SalesOrderActualHoursChartCard(orders, employees, dark, modifier)

@Composable
fun ManhoursByCategoryCardLight(employees: List<EmployeeActivity>, dark: Boolean = false, modifier: Modifier = Modifier) =
    LabourCategoryChartCard(employees, dark = dark, modifier = modifier)

@Composable
fun DailyLabourDeploymentCardDark(employees: List<EmployeeActivity>, modifier: Modifier = Modifier) =
    LabourDeploymentChartCard(employees, dark = true, modifier = modifier)

@Composable
fun ManhoursByCategoryCardDark(employees: List<EmployeeActivity>, modifier: Modifier = Modifier) =
    LabourCategoryChartCard(employees, dark = true, modifier = modifier)

// Previous chart implementations retained temporarily for comparison.
@Composable
private fun DailyLabourDeploymentCardLightLegacy(
    employees: List<EmployeeActivity>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.66f)),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFFD8B4FE))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Daily Labour Deployment Trends (Hours)",
                color = Color(0xFF2E1065),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            val deployment = employees
                .filter { it.hoursClocked > 0.0 }
                .takeLast(6)
            val maxHours = deployment.maxOfOrNull { it.hoursClocked }
                ?.coerceAtLeast(1.0) ?: 1.0

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height
                    val bottomMargin = 24.dp.toPx()
                    val leftMargin = 30.dp.toPx()
                    val chartW = w - leftMargin
                    val chartH = h - bottomMargin

                    val gridCount = 4
                    for (i in 0..gridCount) {
                        val y = (chartH / gridCount) * i
                        drawLine(
                            color = Color(0xFFD8B4FE),
                            start = Offset(leftMargin, y),
                            end = Offset(w, y),
                            strokeWidth = 1f
                        )
                        drawIntoCanvas { canvas ->
                            val paint = android.graphics.Paint().apply {
                                color = android.graphics.Color.parseColor("#64748B")
                                textSize = 8.dp.toPx()
                                textAlign = android.graphics.Paint.Align.RIGHT
                            }
                            val value = maxHours * (gridCount - i) / gridCount
                            canvas.nativeCanvas.drawText(String.format(Locale.US, "%.1f", value), leftMargin - 6.dp.toPx(), y + 3.dp.toPx(), paint)
                        }
                    }

                    val stepW = if (deployment.size > 1) chartW / (deployment.size - 1) else chartW
                    deployment.zipWithNext().forEachIndexed { i, pair ->
                        val startX = leftMargin + i * stepW
                        val endX = leftMargin + (i + 1) * stepW
                        val startY = chartH - (pair.first.hoursClocked / maxHours * chartH).toFloat()
                        val endY = chartH - (pair.second.hoursClocked / maxHours * chartH).toFloat()
                        drawLine(Color(0xFF2E7D32), Offset(startX, startY), Offset(endX, endY), 2.5.dp.toPx())
                    }

                    deployment.forEachIndexed { i, employee ->
                        val cx = if (deployment.size == 1) leftMargin + chartW / 2 else leftMargin + i * stepW
                        val cy = chartH - (employee.hoursClocked / maxHours * chartH).toFloat()
                        drawCircle(
                            color = Color(0xFF2E7D32),
                            radius = 4.dp.toPx(),
                            center = Offset(cx, cy)
                        )
                        drawIntoCanvas { canvas ->
                            val paint = android.graphics.Paint().apply {
                                color = android.graphics.Color.parseColor("#64748B")
                                textSize = 8.dp.toPx()
                                textAlign = android.graphics.Paint.Align.CENTER
                            }
                            val label = employee.empId.ifBlank { employee.name.take(6) }
                            canvas.nativeCanvas.drawText(label, cx, h - 4.dp.toPx(), paint)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF2E7D32))
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Manhours Consumed", color = Color(0xFF5B3A75), fontSize = 11.sp)
            }
        }
    }
}

// Manhours by Labour Category Card (Light)
@Composable
private fun ManhoursByCategoryCardLightLegacy(
    employees: List<EmployeeActivity>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.66f)),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFFD8B4FE))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Manhours by Labour Category",
                color = Color(0xFF2E1065),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            val categoryHours = employees
                .filter { it.hoursClocked > 0.0 }
                .groupBy { it.category.ifBlank { "Unassigned" } }
                .mapValues { (_, workers) -> workers.sumOf { it.hoursClocked } }
                .toList().sortedByDescending { it.second }.take(5)
            val maxHours = categoryHours.maxOfOrNull { it.second }?.coerceAtLeast(1.0) ?: 1.0

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height
                    val bottomMargin = 20.dp.toPx()
                    val leftMargin = 68.dp.toPx()
                    val chartW = w - leftMargin
                    val chartH = h - bottomMargin

                    val gridCount = 4
                    val stepW = chartW / gridCount

                    for (i in 0..gridCount) {
                        val x = leftMargin + i * stepW
                        drawLine(
                            color = Color(0xFFD8B4FE),
                            start = Offset(x, 0f),
                            end = Offset(x, chartH),
                            strokeWidth = 1f
                        )
                        drawIntoCanvas { canvas ->
                            val paint = android.graphics.Paint().apply {
                                color = android.graphics.Color.parseColor("#64748B")
                                textSize = 8.dp.toPx()
                                textAlign = android.graphics.Paint.Align.CENTER
                            }
                            canvas.nativeCanvas.drawText(String.format(Locale.US, "%.1f", maxHours * i / gridCount), x, h - 2.dp.toPx(), paint)
                        }
                    }
                    val slotH = if (categoryHours.isEmpty()) chartH else chartH / categoryHours.size
                    categoryHours.forEachIndexed { index, (category, hours) ->
                        val top = index * slotH + slotH * 0.2f
                        val barH = slotH * 0.6f
                        val barW = (hours / maxHours * chartW).toFloat()
                        drawRoundRect(Color(0xFF6D28D9), Offset(leftMargin, top), Size(barW, barH), CornerRadius(4.dp.toPx()))
                        drawIntoCanvas { canvas ->
                            val paint = android.graphics.Paint().apply {
                                color = android.graphics.Color.parseColor("#5B3A75")
                                textSize = 8.dp.toPx()
                                textAlign = android.graphics.Paint.Align.RIGHT
                            }
                            canvas.nativeCanvas.drawText(category.take(10), leftMargin - 5.dp.toPx(), top + barH * 0.7f, paint)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF6D28D9))
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Hours consumed", color = Color(0xFF5B3A75), fontSize = 11.sp)
            }
        }
    }
}

// Actual vs Planned Cost by Sales Order Card (Dark)
@Composable
fun ActualVsPlannedCostCardDark(
    orders: List<SalesOrder>,
    employees: List<EmployeeActivity>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2E1065)),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFF6D28D9))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = operationalText(currentOperationalLanguage(), "Actual vs. Planned Cost by Sales Order"),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                text = operationalText(currentOperationalLanguage(), "Values in INR (₹)"),
                color = Color(0xFFFEF3C7),
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            val displayOrders = orders.take(5)
            val chartMax = displayOrders.maxOfOrNull {
                maxOf(getPlannedCostForOrder(it), getActualCostForOrder(it, employees))
            }?.coerceAtLeast(1.0)?.toFloat() ?: 1f

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height
                    val bottomMargin = 28.dp.toPx()
                    val leftMargin = 40.dp.toPx()
                    val chartW = w - leftMargin
                    val chartH = h - bottomMargin

                    // Draw Y Grid lines
                    val maxVal = chartMax
                    val gridSteps = 5
                    for (i in 0..gridSteps) {
                        val y = (chartH / gridSteps) * i
                        drawLine(
                            color = Color(0xFF6D28D9),
                            start = Offset(leftMargin, y),
                            end = Offset(w, y),
                            strokeWidth = 1f
                        )
                        val valText = formatCostValue(((gridSteps - i).toDouble() / gridSteps) * maxVal)
                        drawIntoCanvas { canvas ->
                            val paint = android.graphics.Paint().apply {
                                color = android.graphics.Color.parseColor("#8F94B1")
                                textSize = 9.dp.toPx()
                                textAlign = android.graphics.Paint.Align.RIGHT
                            }
                            canvas.nativeCanvas.drawText(valText, leftMargin - 6.dp.toPx(), y + 3.dp.toPx(), paint)
                        }
                    }

                    // Draw Bars for each order
                    val itemCount = displayOrders.size
                    val slotW = chartW / itemCount
                    val barWidth = slotW * 0.32f

                    displayOrders.forEachIndexed { index, ord ->
                        val planned = getPlannedCostForOrder(ord).toFloat()
                        val actual = getActualCostForOrder(ord, employees).toFloat()

                        val plannedH = if (planned > 0f) (planned / maxVal).coerceIn(0f, 1f) * chartH else 0f
                        val actualH = if (actual > 0f) (actual / maxVal).coerceIn(0f, 1f) * chartH else 0f

                        val centerX = leftMargin + index * slotW + slotW / 2
                        val pStartX = centerX - barWidth - 2.dp.toPx()
                        val aStartX = centerX + 2.dp.toPx()

                        // Planned Bar (Dark Grey)
                        drawRoundRect(
                            color = Color(0xFF353D52),
                            topLeft = Offset(pStartX, chartH - plannedH),
                            size = Size(barWidth, plannedH),
                            cornerRadius = CornerRadius(3.dp.toPx(), 3.dp.toPx())
                        )

                        // Actual Bar (Cyan)
                        if (actualH > 0) {
                            drawRoundRect(
                                color = Color(0xFFA855F7),
                                topLeft = Offset(aStartX, chartH - actualH),
                                size = Size(barWidth, actualH),
                                cornerRadius = CornerRadius(3.dp.toPx(), 3.dp.toPx())
                            )
                        }

                        // X-axis Label
                        val labelId = ord.id
                        drawIntoCanvas { canvas ->
                            val paint = android.graphics.Paint().apply {
                                color = android.graphics.Color.parseColor("#8F94B1")
                                textSize = 8.dp.toPx()
                                textAlign = android.graphics.Paint.Align.CENTER
                            }
                            canvas.nativeCanvas.drawText(labelId, centerX, h - 8.dp.toPx(), paint)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Legend
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(10.dp).background(Color(0xFF353D52), RoundedCornerShape(2.dp)))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(operationalText(currentOperationalLanguage(), "Planned Cost"), color = Color(0xFFFEF3C7), fontSize = 11.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(10.dp).background(Color(0xFFA855F7), RoundedCornerShape(2.dp)))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(operationalText(currentOperationalLanguage(), "Actual Cost"), color = Color(0xFFFEF3C7), fontSize = 11.sp)
                }
            }
        }
    }
}

// Cost by Department Donut Chart Card (Dark)
@Composable
fun CostByDepartmentCardDark(
    orders: List<SalesOrder>,
    selectedDept: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2E1065)),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFF6D28D9))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = operationalText(currentOperationalLanguage(), "Cost by Department"),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            val colors = listOf(Color(0xFFA855F7), Color(0xFF00E676), Color(0xFFF59E0B), Color(0xFFA855F7))
            val deptData = orders
                .groupBy { it.department }
                .mapValues { (_, departmentOrders) -> departmentOrders.sumOf { it.plannedBudget } }
                .filterValues { it > 0.0 }
                .entries
                .mapIndexed { index, entry ->
                    (entry.key to entry.value.toFloat()) to colors[index % colors.size]
                }

            val totalVal = deptData.sumOf { it.first.second.toDouble() }.toFloat()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.size(120.dp)) {
                    val strokeW = 22.dp.toPx()
                    var startAngle = -90f

                    deptData.forEach { (pair, color) ->
                        val (deptName, valFactor) = pair
                        val sweep = (valFactor / totalVal) * 360f
                        drawArc(
                            color = color,
                            startAngle = startAngle,
                            sweepAngle = (sweep - 3f).coerceAtLeast(1f), // Gap between slices
                            useCenter = false,
                            style = Stroke(width = strokeW, cap = StrokeCap.Round)
                        )
                        startAngle += sweep
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Legend
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                deptData.forEach { (pair, color) ->
                    val (deptName, _) = pair
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(color)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = deptName,
                            color = Color(0xFFFEF3C7),
                            fontSize = 11.sp,
                            fontWeight = if (selectedDept == deptName) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}

// Daily Labour Deployment Trends Card (Dark)
@Composable
private fun DailyLabourDeploymentCardDarkLegacy(
    employees: List<EmployeeActivity>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2E1065)),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFF6D28D9))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Daily Labour Deployment Trends (Hours)",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            val deployment = employees
                .filter { it.hoursClocked > 0.0 }
                .takeLast(6)
            val maxHours = deployment.maxOfOrNull { it.hoursClocked }
                ?.coerceAtLeast(1.0) ?: 1.0

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height
                    val bottomMargin = 24.dp.toPx()
                    val leftMargin = 30.dp.toPx()
                    val chartW = w - leftMargin
                    val chartH = h - bottomMargin

                    val gridCount = 4
                    for (i in 0..gridCount) {
                        val y = (chartH / gridCount) * i
                        drawLine(
                            color = Color(0xFF6D28D9),
                            start = Offset(leftMargin, y),
                            end = Offset(w, y),
                            strokeWidth = 1f
                        )
                        drawIntoCanvas { canvas ->
                            val paint = android.graphics.Paint().apply {
                                color = android.graphics.Color.parseColor("#8F94B1")
                                textSize = 8.dp.toPx()
                                textAlign = android.graphics.Paint.Align.RIGHT
                            }
                            val value = maxHours * (gridCount - i) / gridCount
                            canvas.nativeCanvas.drawText(String.format(Locale.US, "%.1f", value), leftMargin - 6.dp.toPx(), y + 3.dp.toPx(), paint)
                        }
                    }

                    val stepW = if (deployment.size > 1) chartW / (deployment.size - 1) else chartW
                    deployment.zipWithNext().forEachIndexed { i, pair ->
                        val startX = leftMargin + i * stepW
                        val endX = leftMargin + (i + 1) * stepW
                        val startY = chartH - (pair.first.hoursClocked / maxHours * chartH).toFloat()
                        val endY = chartH - (pair.second.hoursClocked / maxHours * chartH).toFloat()
                        drawLine(Color(0xFF00E676), Offset(startX, startY), Offset(endX, endY), 2.5.dp.toPx())
                    }

                    deployment.forEachIndexed { i, employee ->
                        val cx = if (deployment.size == 1) leftMargin + chartW / 2 else leftMargin + i * stepW
                        val cy = chartH - (employee.hoursClocked / maxHours * chartH).toFloat()
                        drawCircle(
                            color = Color(0xFF00E676),
                            radius = 4.dp.toPx(),
                            center = Offset(cx, cy)
                        )
                        drawIntoCanvas { canvas ->
                            val paint = android.graphics.Paint().apply {
                                color = android.graphics.Color.parseColor("#8F94B1")
                                textSize = 8.dp.toPx()
                                textAlign = android.graphics.Paint.Align.CENTER
                            }
                            val label = employee.empId.ifBlank { employee.name.take(6) }
                            canvas.nativeCanvas.drawText(label, cx, h - 4.dp.toPx(), paint)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00E676))
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Manhours Consumed", color = Color(0xFFFEF3C7), fontSize = 11.sp)
            }
        }
    }
}

// Manhours by Labour Category Card (Dark)
@Composable
private fun ManhoursByCategoryCardDarkLegacy(
    employees: List<EmployeeActivity>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2E1065)),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFF6D28D9))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Manhours by Labour Category",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            val categoryHours = employees
                .filter { it.hoursClocked > 0.0 }
                .groupBy { it.category.ifBlank { "Unassigned" } }
                .mapValues { (_, workers) -> workers.sumOf { it.hoursClocked } }
                .toList().sortedByDescending { it.second }.take(5)
            val maxHours = categoryHours.maxOfOrNull { it.second }?.coerceAtLeast(1.0) ?: 1.0

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height
                    val bottomMargin = 20.dp.toPx()
                    val leftMargin = 68.dp.toPx()
                    val chartW = w - leftMargin
                    val chartH = h - bottomMargin

                    val gridCount = 4
                    val stepW = chartW / gridCount

                    for (i in 0..gridCount) {
                        val x = leftMargin + i * stepW
                        drawLine(
                            color = Color(0xFF6D28D9),
                            start = Offset(x, 0f),
                            end = Offset(x, chartH),
                            strokeWidth = 1f
                        )
                        drawIntoCanvas { canvas ->
                            val paint = android.graphics.Paint().apply {
                                color = android.graphics.Color.parseColor("#8F94B1")
                                textSize = 8.dp.toPx()
                                textAlign = android.graphics.Paint.Align.CENTER
                            }
                            canvas.nativeCanvas.drawText(String.format(Locale.US, "%.1f", maxHours * i / gridCount), x, h - 2.dp.toPx(), paint)
                        }
                    }
                    val slotH = if (categoryHours.isEmpty()) chartH else chartH / categoryHours.size
                    categoryHours.forEachIndexed { index, (category, hours) ->
                        val top = index * slotH + slotH * 0.2f
                        val barH = slotH * 0.6f
                        val barW = (hours / maxHours * chartW).toFloat()
                        drawRoundRect(Color(0xFFA855F7), Offset(leftMargin, top), Size(barW, barH), CornerRadius(4.dp.toPx()))
                        drawIntoCanvas { canvas ->
                            val paint = android.graphics.Paint().apply {
                                color = android.graphics.Color.parseColor("#FEF3C7")
                                textSize = 8.dp.toPx()
                                textAlign = android.graphics.Paint.Align.RIGHT
                            }
                            canvas.nativeCanvas.drawText(category.take(10), leftMargin - 5.dp.toPx(), top + barH * 0.7f, paint)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFA855F7))
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Hours consumed", color = Color(0xFFFEF3C7), fontSize = 11.sp)
            }
        }
    }
}

// Reusable Sales Order Card Component
@Composable
fun SalesOrderCard(
    order: SalesOrder,
    onClick: () -> Unit
) {
    val completionFraction = if (order.targetQty > 0) order.completedQty.toFloat() / order.targetQty.toFloat() else 0f
    val completionPercent = (completionFraction * 100).roundToInt()

    val statusColor = when (order.status) {
        "Completed" -> Color(0xFF2E7D32) // Sleek Green
        "In Progress" -> Color(0xFF6D28D9) // Sleek Purple
        else -> Color(0xFF6B4A7D)
    }

    val deptColor = when (order.department.lowercase()) {
        "assembly" -> Color(0xFF6D28D9)
        "machining" -> Color(0xFFA855F7)
        "quality" -> Color(0xFF2E7D32)
        else -> Color(0xFFF59E0B)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.66f)),
        border = BorderStroke(1.dp, Color(0xFFD8B4FE))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        order.id,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E1065),
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(deptColor.copy(alpha = 0.15f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            order.department,
                            color = deptColor,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Completion percentage banner or icon
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(statusColor.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        order.status.uppercase(),
                        color = statusColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                order.item,
                color = Color(0xFF2E1065),
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Produced: ${order.completedQty} / ${order.targetQty} units",
                    fontSize = 11.sp,
                    color = Color(0xFF5B3A75)
                )
                Text(
                    if (order.timerSeconds > 0) "Timer: ${formatHHMMSS(order.timerSeconds)}" else "$completionPercent%",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (order.timerSeconds > 0) Color(0xFF2E7D32) else if (completionPercent >= 100) Color(0xFF2E7D32) else Color(0xFF2E1065)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            LinearProgressIndicator(
                progress = { completionFraction },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(CircleShape),
                color = statusColor,
                trackColor = Color(0xFFF3E8FF)
            )
        }
    }
}

// Reusable Employee Activity Card Component
@Composable
fun EmployeeActivityCard(
    employee: EmployeeActivity,
    onClick: () -> Unit
) {
    val statusColor = when (employee.status) {
        "Active" -> Color(0xFF2E7D32)
        "Break" -> Color(0xFFF59E0B)
        else -> Color(0xFF6B4A7D)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.66f)),
        border = BorderStroke(1.dp, Color(0xFFD8B4FE))
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Worker Avatar placeholder
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF3E8FF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    tint = statusColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    employee.name,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E1065),
                    fontSize = 14.sp
                )
                Text(
                    "Current Duty: ${employee.task}",
                    fontSize = 11.sp,
                    color = Color(0xFF5B3A75),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Status Chip
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(statusColor.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        employee.status.uppercase(),
                        color = statusColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                val hoursText = if (employee.hoursClocked <= 0.0) {
                    "0 hrs logged"
                } else if (employee.hoursClocked < 1.0) {
                    val totalSecs = (employee.hoursClocked * 3600).toLong()
                    "${formatHHMMSS(totalSecs)} logged"
                } else {
                    "${formatSavedHours(employee.hoursClocked)} hrs logged"
                }
                Text(
                    hoursText,
                    fontSize = 10.sp,
                    color = Color(0xFF6B4A7D)
                )
            }
        }
    }
}

// Reusable Helper for Outlined Borders to avoid bulky imports
fun BoxBorder(color: Color) = BorderStroke(1.dp, color)

// Helper for showing Android DatePickerDialog
fun showDatePicker(context: android.content.Context, initialDateStr: String, onDateSelected: (String) -> Unit) {
    val calendar = java.util.Calendar.getInstance()
    if (initialDateStr.isNotBlank()) {
        val parts = initialDateStr.trim().split("-")
        if (parts.size == 3) {
            val y = parts[0].toIntOrNull()
            val m = parts[1].toIntOrNull()
            val d = parts[2].toIntOrNull()
            if (y != null && m != null && d != null) {
                calendar.set(y, m - 1, d)
            }
        }
    }
    val year = calendar.get(java.util.Calendar.YEAR)
    val month = calendar.get(java.util.Calendar.MONTH)
    val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)

    android.app.DatePickerDialog(
        context,
        { _, selYear, selMonth, selDay ->
            val formatted = String.format(java.util.Locale.US, "%04d-%02d-%02d", selYear, selMonth + 1, selDay)
            onDateSelected(formatted)
        },
        year,
        month,
        day
    ).show()
}

// Exposed Dropdown Menu Custom Selection
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownSelection(
    selectedValue: String,
    label: String,
    options: List<String>,
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedValue,
            onValueChange = {},
            readOnly = true,
            label = { Text(label, color = Color(0xFF5B3A75)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6D28D9),
                unfocusedBorderColor = Color(0xFF6B4A7D),
                focusedTextColor = Color(0xFF2E1065),
                unfocusedTextColor = Color(0xFF2E1065)
            ),
            shape = RoundedCornerShape(12.dp)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.White.copy(alpha = 0.64f))
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option, color = Color(0xFF2E1065)) },
                    onClick = {
                        onValueChange(option)
                        expanded = false
                    },
                    modifier = Modifier.background(Color.White.copy(alpha = 0.64f))
                )
            }
        }
    }
}

@Composable
fun MasterRegistryTab(
    innerPadding: PaddingValues,
    viewModel: DashboardViewModel,
    salesOrders: List<SalesOrder>,
    employees: List<EmployeeActivity>,
    onReleaseNewOrder: () -> Unit,
    onRegisterNewWorker: () -> Unit,
    onOrderSelect: ((SalesOrder) -> Unit)? = null,
    onManageEmployee: ((EmployeeActivity) -> Unit)? = null,
    glassThemeStyle: GlassThemeStyle = GlassThemeStyle.ROYAL_GLASS
) {
    var selectedSubTab by remember { mutableStateOf("Labour Assignment") }
    val context = androidx.compose.ui.platform.LocalContext.current
    val tabBarScrollState = rememberScrollState()
    val tabContentScrollState = rememberScrollState()
    val tabAnimationScope = rememberCoroutineScope()

    // Dynamic lists collected from ViewModel
    val departmentsList by viewModel.departments.collectAsState()
    val categoriesList by viewModel.categories.collectAsState()

    // Dialog flags
    var showAddSalesOrderDialog by remember { mutableStateOf(false) }
    var showAddEmployeeDialog by remember { mutableStateOf(false) }
    var showAddDepartmentDialog by remember { mutableStateOf(false) }
    var showAddCategoryDialog by remember { mutableStateOf(false) }

    // Dialog state variables
    // 1. Sales Order states
    var newSoId by remember { mutableStateOf("") }
    var newSoItem by remember { mutableStateOf("") }
    var newSoCustomer by remember { mutableStateOf("") }
    var newSoQty by remember { mutableStateOf("") }
    var newSoDept by remember { mutableStateOf(departmentsList.firstOrNull()?.name.orEmpty()) }
    var newSoBudget by remember { mutableStateOf("") }
    var newSoHours by remember { mutableStateOf("") }
    var newSoStartDate by remember { mutableStateOf("") }
    var newSoEndDate by remember { mutableStateOf("") }
    var newSoStatus by remember { mutableStateOf("Not Started") }
    var newSoDesc by remember { mutableStateOf("") }

    // 2. Employee states
    var newEmpId by remember { mutableStateOf("") }
    var newEmpName by remember { mutableStateOf("") }
    var newEmpDept by remember { mutableStateOf(departmentsList.firstOrNull()?.name.orEmpty()) }
    var newEmpCategory by remember { mutableStateOf(categoriesList.firstOrNull()?.name.orEmpty()) }
    var newEmpRate by remember { mutableStateOf("") }
    var newEmpSkill by remember { mutableStateOf("") }
    var newEmpStatus by remember { mutableStateOf("Active") }
    var newEmpDuty by remember { mutableStateOf("") }

    // 3. Department states
    var newDeptCode by remember { mutableStateOf("") }
    var newDeptName by remember { mutableStateOf("") }
    var newDeptDesc by remember { mutableStateOf("") }

    // 4. Category states
    var newCatCode by remember { mutableStateOf("") }
    var newCatName by remember { mutableStateOf("") }
    var newCatRate by remember { mutableStateOf("") }

    // Edit dialog states
    var editingSalesOrder by remember { mutableStateOf<SalesOrder?>(null) }
    var editingDepartment by remember { mutableStateOf<Department?>(null) }
    var editingEmployee by remember { mutableStateOf<EmployeeActivity?>(null) }
    var editingCategory by remember { mutableStateOf<LabourCategory?>(null) }

    // Shift employees dialog states
    var showShiftEmployeesDialog by remember { mutableStateOf(false) }
    var shiftSourceOrderId by remember { mutableStateOf("") }
    var shiftSelectedEmployeeIds by remember { mutableStateOf(setOf<String>()) }
    var shiftDestinationOrderId by remember { mutableStateOf("") }
    var shiftMoveTab by remember { mutableStateOf("Existing Order") } // "Existing Order" or "New Order"
    
    // New Order fields for Shift dialog
    var shiftNewOrderId by remember { mutableStateOf("") }
    var shiftNewOrderCustomer by remember { mutableStateOf("") }
    var shiftNewOrderDept by remember { mutableStateOf(departmentsList.firstOrNull()?.name.orEmpty()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(glassBackground(glassThemeStyle))
            .padding(innerPadding)
    ) {
        // Top Navigation Bar with Five Buttons
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White.copy(alpha = 0.64f),
            tonalElevation = 2.dp,
            shadowElevation = 2.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(tabBarScrollState)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val tabs = listOf(
                    "Labour Assignment" to Icons.Filled.Build,
                    "Sales Orders" to Icons.Filled.ShoppingCart,
                    "Departments" to Icons.Filled.Settings,
                    "Employees" to Icons.Filled.Person,
                    "Categories" to Icons.Filled.CheckCircle
                )

                tabs.forEachIndexed { tabIndex, (tabName, icon) ->
                    val isSelected = when (tabName) {
                        "Labour Assignment" -> selectedSubTab == "Labour Assignment"
                        "Sales Orders" -> selectedSubTab == "Sales Order"
                        "Departments" -> selectedSubTab == "Department"
                        "Employees" -> selectedSubTab == "Employee"
                        "Categories" -> selectedSubTab == "Category"
                        else -> false
                    }

                    val containerColor by animateColorAsState(
                        targetValue = if (isSelected) Color(0xFF6D28D9) else Color(0xFFF3E8FF),
                        animationSpec = tween(280, easing = FastOutSlowInEasing),
                        label = "master-tab-background"
                    )
                    val contentColor by animateColorAsState(
                        targetValue = if (isSelected) Color.White else Color(0xFF5B3A75),
                        animationSpec = tween(240, easing = FastOutSlowInEasing),
                        label = "master-tab-content"
                    )

                    Button(
                        onClick = {
                            selectedSubTab = when (tabName) {
                                "Labour Assignment" -> "Labour Assignment"
                                "Sales Orders" -> "Sales Order"
                                "Departments" -> "Department"
                                "Employees" -> "Employee"
                                "Categories" -> "Category"
                                else -> "Labour Assignment"
                            }
                            tabAnimationScope.launch {
                                tabContentScrollState.animateScrollTo(
                                    0,
                                    animationSpec = tween(420, easing = FastOutSlowInEasing)
                                )
                                tabBarScrollState.animateScrollTo(
                                    (tabIndex * 150).coerceAtMost(tabBarScrollState.maxValue),
                                    animationSpec = tween(380, easing = FastOutSlowInEasing)
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = containerColor,
                            contentColor = contentColor
                        ),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        modifier = Modifier.height(40.dp)
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = tabName,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))
                OutlinedButton(
                    onClick = { viewModel.clearAllData() },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFB00020)),
                    border = BorderStroke(1.dp, Color(0xFFB00020).copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier.height(40.dp)
                ) {
                    Icon(Icons.Filled.Delete, contentDescription = "Clear Data", modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Clear All Data", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

            }
        }

        // Sub-Tab Content Area
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(tabContentScrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Dynamic Contextual Mini-Hero Card based on selected tab
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF6D28D9)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    val (title, desc) = when (selectedSubTab) {
                        "Labour Assignment" -> "Labour Assignments & Routing" to "Track live workforce deployment, active job assignments, and clocked hours."
                        "Sales Order" -> "Sales Orders Registry" to "Browse active production runs, monitor target yields, and click to view run details."
                        "Department" -> "Department Station Statistics" to "A complete overview of shop floor stations, headcounts, and completion yields."
                        "Employee" -> "Workforce Roster Database" to "Manage registered operators, view their shift states, and register new personnel."
                        "Category" -> "Operational Categories & Progress" to "Monitor completion metrics grouped by core manufacturing domains and workflows."
                        else -> "Master Registries Database" to "Manage workforce profiles, production runs, and station registrations."
                    }
                    Text(title, color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(desc, color = Color.White.copy(alpha = 0.85f), fontSize = 12.sp)
                }
            }

            // Render Tab Specific UI Content
            when (selectedSubTab) {
                "Labour Assignment" -> {
                    // Labour KPI Card Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val activeCount = employees.filter { it.status == "Active" }.size
                        val totalHrs = employees.sumOf { it.hoursClocked }
                        val totalLabourCost = employees.sumOf { it.hoursClocked * it.hourlyRate }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(Color.White.copy(alpha = 0.64f), RoundedCornerShape(12.dp))
                                .border(1.dp, Color(0xFFD8B4FE), RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Column {
                                Text("ACTIVE LABOUR", fontSize = 9.sp, color = Color(0xFF6B4A7D), fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text("$activeCount Operators", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF2E7D32))
                            }
                        }

                        Box(
                            modifier = Modifier
                                .weight(1.2f)
                                .background(Color.White.copy(alpha = 0.64f), RoundedCornerShape(12.dp))
                                .border(1.dp, Color(0xFFD8B4FE), RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Column {
                                Text("ACCUMULATED LABOUR VALUE", fontSize = 9.sp, color = Color(0xFF6B4A7D), fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(formatCostValue(totalLabourCost), fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF6D28D9))
                            }
                        }
                    }

                    // Labor Assignments grouped by Sales Order
                    val unassignedEmployees = employees.filter { emp ->
                        val isAssigned = salesOrders.any { order -> order.id == emp.task }
                        !isAssigned
                    }

                    // Cards-based Labor Assignments Grouped by Sales Order
                    salesOrders.forEach { order ->
                        val orderEmployees = employees.filter { it.task == order.id }
                        val plannedHrs = order.plannedManhours
                        val actualHrs = orderEmployees.sumOf { it.hoursClocked }
                        val customer = order.description.ifBlank { order.item.ifBlank { "Customer for ${order.id}" } }

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.84f)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(1.dp, Color(0xFFD8B4FE).copy(alpha = 0.9f))
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Box(
                                    modifier = Modifier
                                        .width(44.dp)
                                        .height(4.dp)
                                        .clip(RoundedCornerShape(2.dp))
                                        .background(
                                            Brush.horizontalGradient(
                                                listOf(Color(0xFF6D28D9), Color(0xFFA855F7), Color(0xFFF59E0B))
                                            )
                                        )
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                // 1. Header Row
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = order.id,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = Color(0xFF2E1065),
                                            fontSize = 17.sp,
                                            fontFamily = FontFamily.Monospace
                                        )
                                        Text(
                                            text = customer,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = Color(0xFF6D28D9)
                                        )
                                    }

                                    // Status capsule badge
                                    val statusText = order.status
                                    val (statusColor, statusBg) = when (statusText) {
                                        "Completed" -> Color(0xFF2E7D32) to Color(0xFFE8F5E9)
                                        "In Progress" -> Color(0xFFF59E0B) to Color(0xFFFEF3C7)
                                        "On Hold" -> Color(0xFFF57F17) to Color(0xFFFFF7D6)
                                        else -> Color(0xFF455A64) to Color(0xFFECEFF1)
                                    }
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(statusBg)
                                            .border(1.dp, statusColor.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                            .padding(horizontal = 10.dp, vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = statusText,
                                            color = statusColor,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color(0xFFF4EFF4)))
                                Spacer(modifier = Modifier.height(10.dp))

                                // 2. Content Row: Total Employees & Hours Tracking
                                AdaptiveLanguagePair(
                                    stacked = currentOperationalLanguage() != AppLanguage.ENGLISH,
                                    modifier = Modifier.fillMaxWidth(),
                                    first = { sectionModifier ->
                                    // Total Employees Column
                                    Column(
                                        modifier = sectionModifier
                                            .clip(RoundedCornerShape(14.dp))
                                            .background(Color(0xFFF8F5FF))
                                            .border(1.dp, Color(0xFFE9D5FF), RoundedCornerShape(14.dp))
                                            .padding(12.dp)
                                    ) {
                                        Text(
                                            text = "TOTAL EMPLOYEES",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF6B4A7D),
                                            letterSpacing = 0.5.sp
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            if (orderEmployees.isNotEmpty()) {
                                                OverlappingAvatars(orderEmployees)
                                            } else {
                                                Box(
                                                    modifier = Modifier
                                                        .size(28.dp)
                                                        .clip(CircleShape)
                                                        .background(Color(0xFFF3E8FF)),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Text(
                                                        text = "—",
                                                        color = Color(0xFF6D28D9),
                                                        fontSize = 12.sp,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                }
                                            }

                                            Column {
                                                val empText = if (orderEmployees.size == 1) "1 Employee" else "${orderEmployees.size} Employees"
                                                Text(
                                                    text = empText,
                                                    color = Color(0xFF2E1065),
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 13.sp,
                                                    maxLines = 2,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                                val subtitleText = if (orderEmployees.isNotEmpty()) {
                                                    val firstEmp = orderEmployees.first().name
                                                    if (orderEmployees.size > 1) "$firstEmp +more" else firstEmp
                                                } else {
                                                    "Unassigned"
                                                }
                                                Text(
                                                    text = subtitleText,
                                                    color = Color(0xFF6B4A7D),
                                                    fontSize = 11.sp,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }
                                        }
                                    }
                                    },

                                    // Hours Tracking Column
                                    second = { sectionModifier ->
                                    Column(
                                        modifier = sectionModifier
                                            .clip(RoundedCornerShape(14.dp))
                                            .background(Color(0xFFFFFBF2))
                                            .border(1.dp, Color(0xFFFDE7B2), RoundedCornerShape(14.dp))
                                            .padding(12.dp)
                                    ) {
                                        Text(
                                            text = "HOURS TRACKING",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF6B4A7D),
                                            letterSpacing = 0.5.sp
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "${plannedHrs.toInt()} hrs Planned",
                                                modifier = Modifier.weight(1f),
                                                color = Color(0xFF2E1065),
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 12.sp,
                                                maxLines = 2,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            Text(
                                                text = "${actualHrs.toInt()} / ${plannedHrs.toInt()} hrs",
                                                modifier = Modifier.weight(1f),
                                                color = Color(0xFF6D28D9),
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 12.sp,
                                                textAlign = TextAlign.End,
                                                maxLines = 2,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(6.dp))
                                        val progressVal = if (plannedHrs > 0) (actualHrs / plannedHrs).toFloat().coerceIn(0f, 1f) else 0f
                                        LinearProgressIndicator(
                                            progress = progressVal,
                                            color = Color(0xFF6D28D9),
                                            trackColor = Color(0xFFF3E8FF),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(6.dp)
                                                .clip(RoundedCornerShape(3.dp))
                                        )
                                    }
                                    }
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                // 3. Efficiency and Actions Row
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(Color(0xFFF6F0FF))
                                        .padding(horizontal = 10.dp, vertical = 8.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "EFFICIENCY",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF6B4A7D)
                                        )
                                        Text(
                                            text = "—",
                                            color = Color(0xFF2E7D32),
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 13.sp
                                        )
                                    }

                                    AdaptiveActionButtons(
                                        stacked = currentOperationalLanguage() != AppLanguage.ENGLISH,
                                        showUnassign = orderEmployees.isNotEmpty(),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(end = 64.dp),
                                        unassign = { actionModifier ->
                                            TextButton(
                                                onClick = {
                                                    orderEmployees.forEach { emp ->
                                                        viewModel.updateEmployee(emp.copy(task = "Unassigned"))
                                                    }
                                                },
                                                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFBA1A1A)),
                                                modifier = actionModifier.heightIn(min = 48.dp),
                                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Filled.Delete,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(14.dp)
                                                )
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(
                                                    "Unassign All",
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    textAlign = TextAlign.Center,
                                                    maxLines = 2,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }
                                        },

                                        shift = { actionModifier ->
                                        Button(
                                            onClick = {
                                                shiftSourceOrderId = order.id
                                                shiftSelectedEmployeeIds = emptySet()
                                                shiftDestinationOrderId = ""
                                                showShiftEmployeesDialog = true
                                            },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFFEDE9FE),
                                                contentColor = Color(0xFF2E1065)
                                            ),
                                            shape = RoundedCornerShape(12.dp),
                                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
                                            modifier = actionModifier.heightIn(min = 48.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.SwapHoriz,
                                                contentDescription = null,
                                                modifier = Modifier.size(14.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                "Shift Employees",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.Center,
                                                maxLines = 2,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // 4. Unassigned / Available Operators Card
                    if (unassignedEmployees.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F2FA)),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.dp, Color(0xFFD8B4FE))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "UNASSIGNED",
                                            fontWeight = FontWeight.ExtraBold,
                                            color = Color(0xFF5B3A75),
                                            fontSize = 15.sp,
                                            fontFamily = FontFamily.Monospace
                                        )
                                        Text(
                                            text = "Available Operators",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = Color(0xFF6B4A7D)
                                        )
                                    }

                                    // Available Status capsule badge
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color(0xFFE1F5FE))
                                            .border(1.dp, Color(0xFFA855F7).copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                            .padding(horizontal = 10.dp, vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = "Available",
                                            color = Color(0xFFA855F7),
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))
                                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color(0xFFD8B4FE)))
                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "AVAILABLE OPERATORS",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF6B4A7D),
                                            letterSpacing = 0.5.sp
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            OverlappingAvatars(unassignedEmployees)
                                            Column {
                                                val empText = if (unassignedEmployees.size == 1) "1 Employee" else "${unassignedEmployees.size} Employees"
                                                Text(
                                                    text = empText,
                                                    color = Color(0xFF2E1065),
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 13.sp
                                                )
                                                val firstEmp = unassignedEmployees.first().name
                                                val subtitleText = if (unassignedEmployees.size > 1) "$firstEmp +more" else firstEmp
                                                Text(
                                                    text = subtitleText,
                                                    color = Color(0xFF6B4A7D),
                                                    fontSize = 11.sp
                                                )
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))
                                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color(0xFFD8B4FE)))
                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Button(
                                        onClick = {
                                            shiftSourceOrderId = "UNASSIGNED"
                                            shiftSelectedEmployeeIds = emptySet()
                                            shiftDestinationOrderId = ""
                                            showShiftEmployeesDialog = true
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFFEDE9FE),
                                            contentColor = Color(0xFF2E1065)
                                        ),
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                        modifier = Modifier.height(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.SwapHoriz,
                                            contentDescription = null,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Assign Employees", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }

                "Sales Order" -> {
                    // Quick Action release run
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "ACTIVE SALES ORDERS DATABASE (${salesOrders.size})",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6B4A7D),
                            letterSpacing = 1.sp
                        )

                        Button(
                            onClick = {
                                newSoId = generateNextSalesOrderId(salesOrders)
                                showAddSalesOrderDialog = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFEDE9FE),
                                contentColor = Color(0xFF2E1065)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Add Sales Order", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Interactive overall sales order cards (which navigate to details)
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        salesOrders.forEach { order ->
                            OverallSummaryCard(
                                order = order,
                                employees = employees,
                                onEdit = { editingSalesOrder = order },
                                onDelete = { viewModel.deleteSalesOrder(order.id) }
                            ) {
                                onOrderSelect?.invoke(order)
                            }
                        }
                    }
                }

                "Department" -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "DEPARTMENT STATION REGISTRY (${departmentsList.size})",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6B4A7D),
                            letterSpacing = 1.sp
                        )

                        Button(
                            onClick = { showAddDepartmentDialog = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFEDE9FE),
                                contentColor = Color(0xFF2E1065)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Add Department", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    departmentsList.forEach { deptObj ->
                        val dept = deptObj.name
                        val deptColor = when (dept.lowercase()) {
                            "assembly" -> Color(0xFF6D28D9)
                            "machining" -> Color(0xFFA855F7)
                            "quality" -> Color(0xFF2E7D32)
                            else -> Color(0xFFF59E0B)
                        }

                        val deptEmployees = employees.filter { it.department.equals(dept, ignoreCase = true) }
                        val activeEmployeesCount = deptEmployees.filter { it.status == "Active" }.size
                        val deptOrders = salesOrders.filter { it.department.equals(dept, ignoreCase = true) }
                        val completedRuns = deptOrders.filter { it.status == "Completed" }.size
                        val deptHours = deptEmployees.sumOf { it.hoursClocked }

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.66f)),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, Color(0xFFD8B4FE))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                // Header
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp, 24.dp)
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(deptColor)
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            text = dept.uppercase(),
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 14.sp,
                                            color = Color(0xFF2E1065)
                                        )
                                    }

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(deptColor.copy(alpha = 0.12f))
                                                .padding(horizontal = 8.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                deptObj.code.uppercase(),
                                                color = deptColor,
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = FontFamily.Monospace
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        IconButton(
                                            onClick = { editingDepartment = deptObj },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Edit,
                                                contentDescription = "Edit Department",
                                                tint = Color(0xFF6D28D9),
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                        IconButton(
                                            onClick = { viewModel.deleteDepartment(deptObj.code) },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Delete,
                                                contentDescription = "Delete Department",
                                                tint = Color(0xFFBA1A1A),
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = deptObj.description,
                                    fontSize = 12.sp,
                                    color = Color(0xFF5B3A75)
                                )
                            }
                        }
                    }
                }

                "Employee" -> {
                    // Title and Add button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "WORKFORCE ROSTER LIST (${employees.size})",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6B4A7D),
                            letterSpacing = 1.sp
                        )

                        Button(
                            onClick = {
                                newEmpId = generateNextEmployeeId(employees)
                                showAddEmployeeDialog = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFEDE9FE),
                                contentColor = Color(0xFF2E1065)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Add Employee", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Grid or Column of employees
                    employees.forEach { employee ->
                        val empId = employee.empId.ifEmpty { getEmpIdByName(employee.name) }
                        val rate = employee.hourlyRate
                        val totalEarnings = employee.hoursClocked * rate

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.66f)),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, Color(0xFFD8B4FE))
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Avatar
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFFF3E8FF)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = employee.name.take(1).uppercase(),
                                            color = Color(0xFF6D28D9),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            employee.name,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = Color(0xFF2E1065)
                                        )
                                        Text(
                                            "ID: $empId • Dept: ${employee.department} • Cat: ${employee.category}",
                                            fontSize = 11.sp,
                                            color = Color(0xFF6B4A7D),
                                            fontFamily = FontFamily.Monospace
                                        )
                                        Text(
                                            "Skill Level: ${employee.skillLevel}",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = Color(0xFF6D28D9)
                                        )
                                    }

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        // Status Badge
                                        val statusColor = when (employee.status) {
                                            "Active" -> Color(0xFF2E7D32)
                                            "Break" -> Color(0xFFF59E0B)
                                            else -> Color(0xFF6B4A7D)
                                        }
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(statusColor.copy(alpha = 0.12f))
                                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                        ) {
                                            Text(
                                                employee.status.uppercase(),
                                                color = statusColor,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        IconButton(
                                            onClick = { editingEmployee = employee },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Edit,
                                                contentDescription = "Edit Employee",
                                                tint = Color(0xFF6D28D9),
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                        IconButton(
                                            onClick = { viewModel.deleteEmployee(empId) },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Delete,
                                                contentDescription = "Delete Employee",
                                                tint = Color(0xFFBA1A1A),
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))
                                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color(0xFFF3E8FF)))
                                Spacer(modifier = Modifier.height(10.dp))

                                Column {
                                    Text("ASSIGNED TO SALES ORDER / WORKFLOW", fontSize = 9.sp, color = Color(0xFF6B4A7D), fontWeight = FontWeight.Bold)
                                    Text(
                                        if (employee.task.equals("Assigned Duty", ignoreCase = true) || employee.task.equals("Unassigned", ignoreCase = true) || employee.task.isEmpty()) "— Not Assigned —" else employee.task,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF2E1065)
                                    )
                                }
                            }
                        }
                    }
                }

                "Category" -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "SHOP OPERATIONS BY PROCESS CATEGORY (${categoriesList.size})",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6B4A7D),
                            letterSpacing = 1.sp
                        )

                        Button(
                            onClick = { showAddCategoryDialog = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFEDE9FE),
                                contentColor = Color(0xFF2E1065)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Add Category", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    categoriesList.forEach { catObj ->
                        val catTitle = catObj.name
                        val catTasks = catObj.workflows
                        val deptName = catObj.department

                        val catOrders = salesOrders.filter { it.department.equals(deptName, ignoreCase = true) }
                        val catEmployees = employees.filter { it.department.equals(deptName, ignoreCase = true) }
                        val totalTarget = catOrders.sumOf { it.targetQty }
                        val totalCompleted = catOrders.sumOf { it.completedQty }
                        val completionFraction = if (totalTarget > 0) totalCompleted.toFloat() / totalTarget.toFloat() else 0f
                        val completionPercent = (completionFraction * 100).roundToInt()

                        val icon = when (deptName.lowercase()) {
                            "machining" -> Icons.Filled.Build
                            "assembly" -> Icons.Filled.Settings
                            "quality" -> Icons.Filled.CheckCircle
                            else -> Icons.Filled.ShoppingCart
                        }

                        val themeColor = when (deptName.lowercase()) {
                            "machining" -> Color(0xFFA855F7)
                            "assembly" -> Color(0xFF6D28D9)
                            "quality" -> Color(0xFF2E7D32)
                            else -> Color(0xFFF59E0B)
                        }

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.66f)),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, Color(0xFFD8B4FE))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Filled.CheckCircle,
                                            contentDescription = null,
                                            tint = Color(0xFF6D28D9),
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = catTitle,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = Color(0xFF2E1065)
                                        )
                                    }

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(Color(0xFF6D28D9).copy(alpha = 0.12f))
                                                .padding(horizontal = 8.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                catObj.code,
                                                color = Color(0xFF6D28D9),
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = FontFamily.Monospace
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        IconButton(
                                            onClick = { editingCategory = catObj },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Edit,
                                                contentDescription = "Edit Category",
                                                tint = Color(0xFF6D28D9),
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                        IconButton(
                                            onClick = { viewModel.deleteLabourCategory(catObj.code) },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Delete,
                                                contentDescription = "Delete Category",
                                                tint = Color(0xFFBA1A1A),
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Standard Hourly Rate: ${formatCostValue(catObj.hourlyRate)}/hr",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF5B3A75)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Modal Dialog 1: Add/New Sales Order Dialog
    if (showAddSalesOrderDialog) {
        AlertDialog(
            onDismissRequest = { showAddSalesOrderDialog = false },
            modifier = Modifier.border(1.dp, Color(0xFFD8B4FE), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            title = {
                Text(
                    text = "New Sales Order Details",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E1065)
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .widthIn(max = 400.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = newSoId,
                        onValueChange = { newSoId = it },
                        label = { Text("Sales Order ID (Reference Code)", color = Color(0xFF5B3A75)) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6D28D9),
                            unfocusedBorderColor = Color(0xFF6B4A7D),
                            focusedTextColor = Color(0xFF2E1065),
                            unfocusedTextColor = Color(0xFF2E1065)
                        )
                    )

                    OutlinedTextField(
                        value = newSoCustomer,
                        onValueChange = { newSoCustomer = it },
                        label = { Text("Customer Name", color = Color(0xFF5B3A75)) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6D28D9),
                            unfocusedBorderColor = Color(0xFF6B4A7D),
                            focusedTextColor = Color(0xFF2E1065),
                            unfocusedTextColor = Color(0xFF2E1065)
                        )
                    )

                    OutlinedTextField(
                        value = newSoBudget,
                        onValueChange = { newSoBudget = it },
                        label = { Text("Planned Budget (₹)", color = Color(0xFF5B3A75)) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6D28D9),
                            unfocusedBorderColor = Color(0xFF6B4A7D),
                            focusedTextColor = Color(0xFF2E1065),
                            unfocusedTextColor = Color(0xFF2E1065)
                        )
                    )

                    OutlinedTextField(
                        value = newSoHours,
                        onValueChange = { newSoHours = it },
                        label = { Text("Planned Manhours", color = Color(0xFF5B3A75)) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6D28D9),
                            unfocusedBorderColor = Color(0xFF6B4A7D),
                            focusedTextColor = Color(0xFF2E1065),
                            unfocusedTextColor = Color(0xFF2E1065)
                        )
                    )

                    OutlinedTextField(
                        value = newSoStartDate,
                        onValueChange = { newSoStartDate = it },
                        label = { Text("Start Date (YYYY-MM-DD)", color = Color(0xFF5B3A75)) },
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = {
                                showDatePicker(context, newSoStartDate) { selectedDate ->
                                    newSoStartDate = selectedDate
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Select Start Date",
                                    tint = Color(0xFF6D28D9)
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6D28D9),
                            unfocusedBorderColor = Color(0xFF6B4A7D),
                            focusedTextColor = Color(0xFF2E1065),
                            unfocusedTextColor = Color(0xFF2E1065)
                        )
                    )

                    OutlinedTextField(
                        value = newSoEndDate,
                        onValueChange = { newSoEndDate = it },
                        label = { Text("End Date (YYYY-MM-DD)", color = Color(0xFF5B3A75)) },
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = {
                                showDatePicker(context, newSoEndDate) { selectedDate ->
                                    newSoEndDate = selectedDate
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Select End Date",
                                    tint = Color(0xFF6D28D9)
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6D28D9),
                            unfocusedBorderColor = Color(0xFF6B4A7D),
                            focusedTextColor = Color(0xFF2E1065),
                            unfocusedTextColor = Color(0xFF2E1065)
                        )
                    )

                    ExposedDropdownSelection(
                        selectedValue = newSoStatus,
                        label = "Release Status",
                        options = listOf("Not Started", "In Progress", "Completed")
                    ) {
                        newSoStatus = it
                    }

                    OutlinedTextField(
                        value = newSoDesc,
                        onValueChange = { newSoDesc = it },
                        label = { Text("Brief Work Description", color = Color(0xFF5B3A75)) },
                        maxLines = 3,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6D28D9),
                            unfocusedBorderColor = Color(0xFF6B4A7D),
                            focusedTextColor = Color(0xFF2E1065),
                            unfocusedTextColor = Color(0xFF2E1065)
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val budget = newSoBudget.toDoubleOrNull() ?: 7500.0
                        val hrs = newSoHours.toDoubleOrNull() ?: 45.0
                        if (newSoId.trim().isNotEmpty() && newSoCustomer.trim().isNotEmpty()) {
                            viewModel.addNewTask(
                                id = newSoId.trim().uppercase(),
                                item = newSoCustomer.trim(),
                                targetQty = 100,
                                department = "Machining",
                                description = newSoDesc.trim(),
                                plannedManhours = hrs,
                                plannedBudget = budget,
                                startDate = newSoStartDate.trim(),
                                endDate = newSoEndDate.trim(),
                                status = newSoStatus
                            )
                            // Reset state & close
                            newSoDesc = ""
                            showAddSalesOrderDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6D28D9)),
                    shape = RoundedCornerShape(8.dp),
                    enabled = newSoId.trim().isNotEmpty() && newSoCustomer.trim().isNotEmpty()
                ) {
                    Text("Save", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showAddSalesOrderDialog = false },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF5B3A75)),
                    border = BorderStroke(1.dp, Color(0xFFD8B4FE)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Cancel")
                }
            },
            containerColor = Color.White.copy(alpha = 0.76f)
        )
    }

    // Modal Dialog 2: Add/New Employee Dialog
    if (showAddEmployeeDialog) {
        EmployeeFormModalDialog(
            title = "New Employee",
            initialEmpId = newEmpId,
            initialName = newEmpName,
            initialDept = newEmpDept,
            initialCategory = newEmpCategory,
            initialSkill = newEmpSkill.ifEmpty { "Expert" },
            initialStatus = newEmpStatus.ifEmpty { "Active" },
            initialAssignedSo = newEmpDuty,
            departmentsList = departmentsList,
            categoriesList = categoriesList,
            salesOrders = salesOrders,
            allEmployees = employees,
            onDismiss = { showAddEmployeeDialog = false },
            onSave = { empId, name, dept, cat, skill, status, assignedSo ->
                val selectedCatObj = categoriesList.find { it.name == cat }
                val rate = selectedCatObj?.hourlyRate ?: 0.0
                viewModel.addNewEmployee(
                    empId = empId,
                    name = name,
                    department = dept,
                    category = cat,
                    task = if (assignedSo == "Unassigned" || assignedSo == "Assigned Duty") "Unassigned" else assignedSo,
                    status = status,
                    hourlyRate = rate,
                    skillLevel = skill
                )
                newEmpName = ""
                newEmpDuty = "Assigned Duty"
                showAddEmployeeDialog = false
            }
        )
    }

    // Modal Dialog 3: Add/New Department Dialog
    if (showAddDepartmentDialog) {
        AlertDialog(
            onDismissRequest = { showAddDepartmentDialog = false },
            modifier = Modifier.border(1.dp, Color(0xFFD8B4FE), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            title = {
                Text(
                    text = "New Department Station Details",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E1065)
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .widthIn(max = 400.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = newDeptCode,
                        onValueChange = { newDeptCode = it },
                        label = { Text("Department Station Code", color = Color(0xFF5B3A75)) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6D28D9),
                            unfocusedBorderColor = Color(0xFF6B4A7D),
                            focusedTextColor = Color(0xFF2E1065),
                            unfocusedTextColor = Color(0xFF2E1065)
                        )
                    )

                    OutlinedTextField(
                        value = newDeptName,
                        onValueChange = { newDeptName = it },
                        label = { Text("Department Station Name", color = Color(0xFF5B3A75)) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6D28D9),
                            unfocusedBorderColor = Color(0xFF6B4A7D),
                            focusedTextColor = Color(0xFF2E1065),
                            unfocusedTextColor = Color(0xFF2E1065)
                        )
                    )

                    OutlinedTextField(
                        value = newDeptDesc,
                        onValueChange = { newDeptDesc = it },
                        label = { Text("Detailed Station Description", color = Color(0xFF5B3A75)) },
                        maxLines = 3,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6D28D9),
                            unfocusedBorderColor = Color(0xFF6B4A7D),
                            focusedTextColor = Color(0xFF2E1065),
                            unfocusedTextColor = Color(0xFF2E1065)
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newDeptCode.trim().isNotEmpty() && newDeptName.trim().isNotEmpty()) {
                            viewModel.addNewDepartment(
                                code = newDeptCode.trim().uppercase(),
                                name = newDeptName.trim(),
                                description = newDeptDesc.trim()
                            )
                            newDeptCode = ""
                            newDeptName = ""
                            newDeptDesc = ""
                            showAddDepartmentDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6D28D9)),
                    shape = RoundedCornerShape(8.dp),
                    enabled = newDeptCode.trim().isNotEmpty() && newDeptName.trim().isNotEmpty()
                ) {
                    Text("Save", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showAddDepartmentDialog = false },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF5B3A75)),
                    border = BorderStroke(1.dp, Color(0xFFD8B4FE)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Cancel")
                }
            },
            containerColor = Color.White.copy(alpha = 0.76f)
        )
    }

    // Modal Dialog 4: Add/New Category Dialog
    if (showAddCategoryDialog) {
        AlertDialog(
            onDismissRequest = { showAddCategoryDialog = false },
            modifier = Modifier.border(1.dp, Color(0xFFD8B4FE), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            title = {
                Text(
                    text = "New Labour Category Details",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E1065)
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .widthIn(max = 400.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = newCatCode,
                        onValueChange = { newCatCode = it },
                        label = { Text("Category Reference Code", color = Color(0xFF5B3A75)) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6D28D9),
                            unfocusedBorderColor = Color(0xFF6B4A7D),
                            focusedTextColor = Color(0xFF2E1065),
                            unfocusedTextColor = Color(0xFF2E1065)
                        )
                    )

                    OutlinedTextField(
                        value = newCatName,
                        onValueChange = { newCatName = it },
                        label = { Text("Category Display Name", color = Color(0xFF5B3A75)) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6D28D9),
                            unfocusedBorderColor = Color(0xFF6B4A7D),
                            focusedTextColor = Color(0xFF2E1065),
                            unfocusedTextColor = Color(0xFF2E1065)
                        )
                    )

                    OutlinedTextField(
                        value = newCatRate,
                        onValueChange = { newCatRate = it },
                        label = { Text("Standard Hourly Rate (₹/hr)", color = Color(0xFF5B3A75)) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6D28D9),
                            unfocusedBorderColor = Color(0xFF6B4A7D),
                            focusedTextColor = Color(0xFF2E1065),
                            unfocusedTextColor = Color(0xFF2E1065)
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val rate = newCatRate.toDoubleOrNull() ?: 45.0
                        if (newCatCode.trim().isNotEmpty() && newCatName.trim().isNotEmpty()) {
                            viewModel.addNewCategory(
                                code = newCatCode.trim().uppercase(),
                                name = newCatName.trim(),
                                hourlyRate = rate,
                                workflows = emptyList(),
                                department = departmentsList.firstOrNull()?.name ?: "Machining"
                            )
                            newCatCode = ""
                            newCatName = ""
                            showAddCategoryDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6D28D9)),
                    shape = RoundedCornerShape(8.dp),
                    enabled = newCatCode.trim().isNotEmpty() && newCatName.trim().isNotEmpty()
                ) {
                    Text("Save", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showAddCategoryDialog = false },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF5B3A75)),
                    border = BorderStroke(1.dp, Color(0xFFD8B4FE)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Cancel")
                }
            },
            containerColor = Color.White.copy(alpha = 0.76f)
        )
    }

    // Modal Dialog 5: Edit Sales Order Dialog
    editingSalesOrder?.let { order ->
        var editSoCustomer by remember(order) { mutableStateOf(order.item) }
        var editSoBudget by remember(order) { mutableStateOf(order.plannedBudget.toString()) }
        var editSoHours by remember(order) { mutableStateOf(order.plannedManhours.toString()) }
        var editSoStartDate by remember(order) { mutableStateOf(order.startDate) }
        var editSoEndDate by remember(order) { mutableStateOf(order.endDate) }
        var editSoStatus by remember(order) { mutableStateOf(order.status) }
        var editSoDesc by remember(order) { mutableStateOf(order.description) }
        val editSoId = order.id

        AlertDialog(
            onDismissRequest = { editingSalesOrder = null },
            modifier = Modifier.border(1.dp, Color(0xFFD8B4FE), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            title = { Text("Edit Sales Order ${order.id}", fontWeight = FontWeight.Bold, color = Color(0xFF2E1065)) },
            text = {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()).widthIn(max = 400.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(value = editSoId, onValueChange = {}, label = { Text("Sales Order ID") }, readOnly = true, singleLine = true, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6D28D9)))
                    OutlinedTextField(
                        value = editSoCustomer,
                        onValueChange = { editSoCustomer = it },
                        label = { Text("Customer Name") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6D28D9))
                    )
                    OutlinedTextField(
                        value = editSoBudget,
                        onValueChange = { editSoBudget = it },
                        label = { Text("Planned Budget (₹)") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6D28D9))
                    )
                    OutlinedTextField(
                        value = editSoHours,
                        onValueChange = { editSoHours = it },
                        label = { Text("Planned Manhours") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6D28D9))
                    )
                    OutlinedTextField(
                        value = editSoStartDate,
                        onValueChange = { editSoStartDate = it },
                        label = { Text("Start Date") },
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = {
                                showDatePicker(context, editSoStartDate) { selectedDate ->
                                    editSoStartDate = selectedDate
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Select Start Date",
                                    tint = Color(0xFF6D28D9)
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6D28D9))
                    )
                    OutlinedTextField(
                        value = editSoEndDate,
                        onValueChange = { editSoEndDate = it },
                        label = { Text("End Date") },
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = {
                                showDatePicker(context, editSoEndDate) { selectedDate ->
                                    editSoEndDate = selectedDate
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Select End Date",
                                    tint = Color(0xFF6D28D9)
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6D28D9))
                    )
                    ExposedDropdownSelection(
                        selectedValue = editSoStatus,
                        label = "Release Status",
                        options = listOf("Not Started", "In Progress", "Completed")
                    ) {
                        editSoStatus = it
                    }
                    OutlinedTextField(
                        value = editSoDesc,
                        onValueChange = { editSoDesc = it },
                        label = { Text("Description") },
                        maxLines = 3,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6D28D9))
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val updated = order.copy(
                            item = editSoCustomer.trim(),
                            plannedBudget = editSoBudget.toDoubleOrNull() ?: order.plannedBudget,
                            plannedManhours = editSoHours.toDoubleOrNull() ?: order.plannedManhours,
                            startDate = editSoStartDate.trim(),
                            endDate = editSoEndDate.trim(),
                            status = editSoStatus,
                            description = editSoDesc.trim()
                        )
                        viewModel.updateSalesOrder(updated)
                        editingSalesOrder = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6D28D9)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Save Changes")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { editingSalesOrder = null },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF5B3A75)),
                    border = BorderStroke(1.dp, Color(0xFFD8B4FE)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Cancel")
                }
            },
            containerColor = Color.White.copy(alpha = 0.76f)
        )
    }

    // Modal Dialog 6: Edit Department Dialog
    editingDepartment?.let { dept ->
        var editDeptName by remember(dept) { mutableStateOf(dept.name) }
        var editDeptDesc by remember(dept) { mutableStateOf(dept.description) }
        val editDeptId = dept.code

        AlertDialog(
            onDismissRequest = { editingDepartment = null },
            modifier = Modifier.border(1.dp, Color(0xFFD8B4FE), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            title = { Text("Edit Department ${dept.code}", fontWeight = FontWeight.Bold, color = Color(0xFF2E1065)) },
            text = {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()).widthIn(max = 400.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(value = editDeptId, onValueChange = {}, label = { Text("Department ID") }, readOnly = true, singleLine = true, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6D28D9)))
                    OutlinedTextField(
                        value = editDeptName,
                        onValueChange = { editDeptName = it },
                        label = { Text("Department Name") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6D28D9))
                    )
                    OutlinedTextField(
                        value = editDeptDesc,
                        onValueChange = { editDeptDesc = it },
                        label = { Text("Description") },
                        maxLines = 3,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6D28D9))
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val updated = dept.copy(
                            name = editDeptName.trim(),
                            description = editDeptDesc.trim()
                        )
                        viewModel.updateDepartment(updated, oldCode = dept.code)
                        editingDepartment = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6D28D9)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Save Changes")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { editingDepartment = null },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF5B3A75)),
                    border = BorderStroke(1.dp, Color(0xFFD8B4FE)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Cancel")
                }
            },
            containerColor = Color.White.copy(alpha = 0.76f)
        )
    }

    // Modal Dialog 7: Edit Category Dialog
    editingCategory?.let { cat ->
        var editCatName by remember(cat) { mutableStateOf(cat.name) }
        var editCatRate by remember(cat) { mutableStateOf(cat.hourlyRate.toString()) }
        var editCatDepartment by remember(cat) { mutableStateOf(cat.department) }
        val editCatId = cat.code

        AlertDialog(
            onDismissRequest = { editingCategory = null },
            modifier = Modifier.border(1.dp, Color(0xFFD8B4FE), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            title = { Text("Edit Category ${cat.code}", fontWeight = FontWeight.Bold, color = Color(0xFF2E1065)) },
            text = {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()).widthIn(max = 400.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(value = editCatId, onValueChange = {}, label = { Text("Category ID") }, readOnly = true, singleLine = true, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6D28D9)))
                    OutlinedTextField(
                        value = editCatName,
                        onValueChange = { editCatName = it },
                        label = { Text("Category Name") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6D28D9))
                    )
                    OutlinedTextField(
                        value = editCatRate,
                        onValueChange = { editCatRate = it },
                        label = { Text("Hourly Rate (₹/hr)") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6D28D9))
                    )
                    ExposedDropdownSelection(
                        selectedValue = editCatDepartment,
                        label = "Department",
                        options = departmentsList.map { it.name }
                    ) { editCatDepartment = it }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val updated = cat.copy(
                            name = editCatName.trim(),
                            hourlyRate = editCatRate.toDoubleOrNull() ?: cat.hourlyRate,
                            department = editCatDepartment
                        )
                        viewModel.updateLabourCategory(updated, oldCode = cat.code)
                        editingCategory = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6D28D9)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Save Changes")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { editingCategory = null },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF5B3A75)),
                    border = BorderStroke(1.dp, Color(0xFFD8B4FE)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Cancel")
                }
            },
            containerColor = Color.White.copy(alpha = 0.76f)
        )
    }

    // Modal Dialog 8: Edit Employee Dialog
    editingEmployee?.let { emp ->
        EmployeeFormModalDialog(
            title = "Edit Employee",
            initialEmpId = emp.empId.ifEmpty { getEmpIdByName(emp.name) },
            initialName = emp.name,
            initialDept = emp.department,
            initialCategory = emp.category,
            initialSkill = emp.skillLevel,
            initialStatus = emp.status,
            initialAssignedSo = emp.task,
            departmentsList = departmentsList,
            categoriesList = categoriesList,
            salesOrders = salesOrders,
            allEmployees = employees,
            onDismiss = { editingEmployee = null },
            onSave = { empId, name, dept, cat, skill, status, assignedSo ->
                val selectedCatObj = categoriesList.find { it.name == cat }
                val rate = selectedCatObj?.hourlyRate ?: emp.hourlyRate
                val updated = emp.copy(
                    empId = empId,
                    name = name,
                    department = dept,
                    category = cat,
                    hourlyRate = rate,
                    skillLevel = skill,
                    status = status,
                    task = if (assignedSo == "Unassigned") "Unassigned" else assignedSo
                )
                viewModel.updateEmployee(updated)
                editingEmployee = null
            }
        )
    }

    // Modal Dialog 9: Shift Employees Dialog
    if (showShiftEmployeesDialog) {
        val sourceEmployees = if (shiftSourceOrderId == "UNASSIGNED") {
            employees.filter { emp -> !salesOrders.any { order -> order.id == emp.task } }
        } else {
            employees.filter { it.task == shiftSourceOrderId }
        }

        AlertDialog(
            onDismissRequest = { showShiftEmployeesDialog = false },
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Switch / Move Employees", fontWeight = FontWeight.Bold, color = Color(0xFF2E1065), fontSize = 16.sp)
                    IconButton(onClick = { showShiftEmployeesDialog = false }) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
                    }
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .widthIn(max = 420.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Source Order Info Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.72f))
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                "SOURCE SALES ORDER",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF6D28D9)
                            )
                            Text(
                                text = if (shiftSourceOrderId == "UNASSIGNED") "Unassigned / Available Operators" else shiftSourceOrderId,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color(0xFF2E1065)
                            )
                        }
                    }

                    // Employees in this order
                    Text(
                        "EMPLOYEES IN THIS ORDER",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6D28D9)
                    )

                    if (sourceEmployees.isEmpty()) {
                        Text("No employees currently assigned.", fontSize = 11.sp, color = Color(0xFF6B4A7D))
                    } else {
                        // Select All row
                        val allSelected = shiftSelectedEmployeeIds.size == sourceEmployees.size
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    shiftSelectedEmployeeIds = if (allSelected) {
                                        emptySet()
                                    } else {
                                        sourceEmployees.map { it.empId }.toSet()
                                    }
                                }
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = allSelected,
                                    onCheckedChange = { checked ->
                                        shiftSelectedEmployeeIds = if (checked) {
                                            sourceEmployees.map { it.empId }.toSet()
                                        } else {
                                            emptySet()
                                        }
                                    }
                                )
                                Text("Select All", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                            Text(
                                text = "${shiftSelectedEmployeeIds.size} selected",
                                fontSize = 11.sp,
                                color = Color(0xFF6B4A7D)
                            )
                        }

                        // Employee multi-select list
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            sourceEmployees.forEach { emp ->
                                val isChecked = shiftSelectedEmployeeIds.contains(emp.empId)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            if (isChecked) Color(0xFF6D28D9).copy(alpha = 0.05f) else Color.Transparent,
                                            RoundedCornerShape(8.dp)
                                        )
                                        .border(
                                            1.dp,
                                            if (isChecked) Color(0xFF6D28D9).copy(alpha = 0.3f) else Color(0xFFD8B4FE),
                                            RoundedCornerShape(8.dp)
                                        )
                                        .clickable {
                                            shiftSelectedEmployeeIds = if (isChecked) {
                                                shiftSelectedEmployeeIds - emp.empId
                                            } else {
                                                shiftSelectedEmployeeIds + emp.empId
                                            }
                                        }
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = isChecked,
                                        onCheckedChange = { checked ->
                                            shiftSelectedEmployeeIds = if (checked) {
                                                shiftSelectedEmployeeIds + emp.empId
                                            } else {
                                                shiftSelectedEmployeeIds - emp.empId
                                            }
                                        }
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFFEDE9FE)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            emp.name.take(1).uppercase(),
                                            color = Color(0xFF2E1065),
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = emp.name,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF2E1065)
                                        )
                                        Text(
                                            text = "${emp.department} • ${emp.category}",
                                            fontSize = 10.sp,
                                            color = Color(0xFF6B4A7D)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Move to destination section
                    Text(
                        "MOVE SELECTED EMPLOYEES TO",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6D28D9)
                    )

                    // Sub-tabs: Existing Order vs New Order
                    TabRow(
                        selectedTabIndex = if (shiftMoveTab == "Existing Order") 0 else 1,
                        modifier = Modifier.fillMaxWidth().height(36.dp),
                        containerColor = Color.Transparent,
                        contentColor = Color(0xFF6D28D9),
                        indicator = { tabPositions ->
                            TabRowDefaults.SecondaryIndicator(
                                Modifier.tabIndicatorOffset(tabPositions[if (shiftMoveTab == "Existing Order") 0 else 1]),
                                color = Color(0xFF6D28D9)
                            )
                        }
                    ) {
                        Tab(
                            selected = shiftMoveTab == "Existing Order",
                            onClick = { shiftMoveTab = "Existing Order" }
                        ) {
                            Text("Existing Order", fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 6.dp))
                        }
                        Tab(
                            selected = shiftMoveTab == "New Order",
                            onClick = { shiftMoveTab = "New Order" }
                        ) {
                            Text("New Order", fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 6.dp))
                        }
                    }

                    if (shiftMoveTab == "Existing Order") {
                        val otherOrders = salesOrders.filter { it.id != shiftSourceOrderId }
                        ExposedDropdownSelection(
                            selectedValue = if (shiftDestinationOrderId.isEmpty()) "Select Destination..." else shiftDestinationOrderId,
                            label = "Destination Sales Order *",
                            options = otherOrders.map { it.id }
                        ) {
                            shiftDestinationOrderId = it
                        }
                    } else {
                        // Create New Order inline fields
                        OutlinedTextField(
                            value = shiftNewOrderId,
                            onValueChange = { shiftNewOrderId = it },
                            label = { Text("Sales Order ID *", color = Color(0xFF5B3A75)) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6D28D9))
                        )
                        OutlinedTextField(
                            value = shiftNewOrderCustomer,
                            onValueChange = { shiftNewOrderCustomer = it },
                            label = { Text("Customer Name *", color = Color(0xFF5B3A75)) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6D28D9))
                        )
                        ExposedDropdownSelection(
                            selectedValue = shiftNewOrderDept,
                            label = "Department Station *",
                            options = departmentsList.map { it.name }
                        ) {
                            shiftNewOrderDept = it
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (shiftSelectedEmployeeIds.isNotEmpty()) {
                            if (shiftMoveTab == "Existing Order") {
                                if (shiftDestinationOrderId.isNotEmpty()) {
                                    viewModel.moveEmployees(shiftSelectedEmployeeIds.toList(), shiftDestinationOrderId)
                                    showShiftEmployeesDialog = false
                                }
                            } else {
                                if (shiftNewOrderId.trim().isNotEmpty() && shiftNewOrderCustomer.trim().isNotEmpty()) {
                                    val newId = shiftNewOrderId.trim().uppercase()
                                    viewModel.addNewTask(
                                        id = newId,
                                        item = shiftNewOrderCustomer.trim(),
                                        targetQty = 100,
                                        department = shiftNewOrderDept,
                                        description = "Created via Shift Workflow",
                                        plannedManhours = 40.0,
                                        plannedBudget = 6000.0,
                                        startDate = "2026-07-21",
                                        endDate = "2026-07-31",
                                        status = "In Progress"
                                    )
                                    viewModel.moveEmployees(shiftSelectedEmployeeIds.toList(), newId)
                                    // Reset fields
                                    shiftNewOrderId = ""
                                    shiftNewOrderCustomer = ""
                                    showShiftEmployeesDialog = false
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6D28D9)),
                    enabled = shiftSelectedEmployeeIds.isNotEmpty() && (
                        (shiftMoveTab == "Existing Order" && shiftDestinationOrderId.isNotEmpty()) ||
                        (shiftMoveTab == "New Order" && shiftNewOrderId.trim().isNotEmpty() && shiftNewOrderCustomer.trim().isNotEmpty())
                    )
                ) {
                    Icon(imageVector = Icons.Filled.Done, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Move Employees")
                }
            },
            dismissButton = {
                TextButton(onClick = { showShiftEmployeesDialog = false }) {
                    Text("Cancel", color = Color(0xFF6D28D9))
                }
            },
            containerColor = Color.White.copy(alpha = 0.72f)
        )
    }
}

// Data Models for Supervisor Management
data class SupervisorEmployeeItem(
    val empId: String,
    var name: String,
    var department: String,
    var category: String,
    var skillLevel: String,
    var hourlyRate: Double,
    var assignmentStatus: String = "Assigned",
    var actualSeconds: Long = 0L
)

data class SalesOrderSupervisorCard(
    val soNumber: String,
    var projectName: String,
    var customerName: String,
    var status: String,            // "Running", "Paused", "Completed", "Delayed", "Not Started"
    var plannedHours: Int,
    var plannedBudget: Double = 0.0,
    var timerSeconds: Long = 0L,
    var isTimerRunning: Boolean = false,
    var department: String,
    var category: String,
    val assignedEmployees: MutableList<SupervisorEmployeeItem> = mutableListOf(),
    val id: String = soNumber
)

fun formatHHMMSS(seconds: Long): String {
    val h = seconds / 3600
    val m = (seconds % 3600) / 60
    val s = seconds % 60
    return String.format(Locale.US, "%02d:%02d:%02d", h, m, s)
}

private fun timerSecondsToHours(seconds: Long): Double =
    seconds.coerceAtLeast(0L).toDouble() / 3600.0

private fun formatSavedHours(hours: Double): String =
    String.format(Locale.US, "%.4f", hours.coerceAtLeast(0.0))

private fun currentIsoTimestamp(): String =
    java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.US).format(java.util.Date())

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupervisorTab(
    innerPadding: PaddingValues,
    viewModel: DashboardViewModel,
    salesOrders: List<SalesOrder>,
    employees: List<EmployeeActivity>,
    userEmail: String?,
    glassThemeStyle: GlassThemeStyle = GlassThemeStyle.ROYAL_GLASS
) {
    val context = LocalContext.current
    val departmentsList by viewModel.departments.collectAsState()
    val categoriesList by viewModel.categories.collectAsState()
    val voiceTimerSelections by viewModel.voiceTimerSelections.collectAsState()

    // Master List of Available Company Employees (derived dynamically from ViewModel)
    val masterEmployeePool = remember(employees) {
        employees.map { emp ->
            SupervisorEmployeeItem(
                empId = if (emp.empId.isNotEmpty()) emp.empId else "EMP-${emp.name.hashCode().toString().takeLast(4)}",
                name = emp.name,
                department = emp.department,
                category = emp.category,
                skillLevel = emp.skillLevel,
                hourlyRate = emp.hourlyRate,
                assignmentStatus = if (emp.task.isNotEmpty() && emp.task != "Unassigned" && emp.task != "Assigned Duty") "Assigned to ${emp.task}" else "Unassigned",
                actualSeconds = (emp.hoursClocked * 3600).toLong()
            )
        }
    }

    // Sales Orders list for Supervisor Tab
    val ordersList = remember { mutableStateListOf<SalesOrderSupervisorCard>() }

    // Synchronize ordersList with ViewModel salesOrders and employees
    LaunchedEffect(salesOrders, employees) {
        val currentSoNumbersInVm = salesOrders.map { it.id }.toSet()
        ordersList.removeAll { it.soNumber !in currentSoNumbersInVm }

        for (so in salesOrders) {
            var card = ordersList.find { it.soNumber == so.id }
            val assignedFromVm = employees.filter { it.task == so.id }.map { emp ->
                SupervisorEmployeeItem(
                    empId = if (emp.empId.isNotEmpty()) emp.empId else "EMP-${emp.name.hashCode().toString().takeLast(4)}",
                    name = emp.name,
                    department = emp.department,
                    category = emp.category,
                    skillLevel = emp.skillLevel,
                    hourlyRate = emp.hourlyRate,
                    assignmentStatus = "Assigned to ${so.id}",
                    actualSeconds = (emp.hoursClocked * 3600).toLong()
                )
            }

            if (card == null) {
                ordersList.add(
                    SalesOrderSupervisorCard(
                        soNumber = so.id,
                        projectName = so.item,
                        customerName = so.description.ifEmpty { "Customer for ${so.id}" },
                        status = so.status,
                        plannedHours = so.plannedManhours.toInt(),
                        plannedBudget = so.plannedBudget,
                        timerSeconds = so.timerSeconds,
                        isTimerRunning = so.status == "Running",
                        department = so.department,
                        category = "General",
                        assignedEmployees = assignedFromVm.toMutableList()
                    )
                )
            } else {
                card.projectName = so.item
                card.customerName = so.description.ifEmpty { "Customer for ${so.id}" }
                card.department = so.department
                card.plannedHours = so.plannedManhours.toInt()
                card.plannedBudget = so.plannedBudget
                if (so.status == "Running") {
                    if (!card.isTimerRunning) card.timerSeconds = so.timerSeconds
                    card.isTimerRunning = true
                    card.status = "Running"
                } else {
                    card.isTimerRunning = false
                    card.timerSeconds = so.timerSeconds
                    card.status = so.status
                }
                for (vmEmp in assignedFromVm) {
                    val existing = card.assignedEmployees.find { it.empId == vmEmp.empId }
                    if (existing == null) {
                        card.assignedEmployees.add(vmEmp)
                    } else {
                        existing.name = vmEmp.name
                        existing.department = vmEmp.department
                        existing.category = vmEmp.category
                        existing.skillLevel = vmEmp.skillLevel
                        existing.hourlyRate = vmEmp.hourlyRate
                        if (vmEmp.actualSeconds > existing.actualSeconds) {
                            existing.actualSeconds = vmEmp.actualSeconds
                        }
                    }
                }
                val vmEmpIds = assignedFromVm.map { it.empId }.toSet()
                card.assignedEmployees.removeAll { it.empId !in vmEmpIds }
            }
        }
    }

    // Selected Employees Checkboxes state (Key: "${soNumber}_${empId}")
    val selectedEmployeeKeys = remember { mutableStateListOf<String>() }
    var previousVoiceKeys by remember { mutableStateOf(emptySet<String>()) }
    LaunchedEffect(voiceTimerSelections) {
        selectedEmployeeKeys.removeAll { it in previousVoiceKeys }
        val currentVoiceKeys = voiceTimerSelections.flatMap { (orderId, employeeIds) ->
            employeeIds.split("|").filter { it.isNotBlank() }.map { "${orderId}_${it}" }
        }.toSet()
        currentVoiceKeys.forEach { key ->
            if (!selectedEmployeeKeys.contains(key)) selectedEmployeeKeys.add(key)
        }
        previousVoiceKeys = currentVoiceKeys
    }

    // Ticker state to force recomposition for running timers every second
    var tickState by remember { mutableStateOf(0L) }
    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(1000L)
            tickState++
            for (order in ordersList) {
                if (order.isTimerRunning) {
                    order.timerSeconds++
                    
                    for (emp in order.assignedEmployees) {
                        val key = "${order.soNumber}_${emp.empId}"
                        if (selectedEmployeeKeys.contains(key)) {
                            emp.actualSeconds++
                            viewModel.updateEmployeeHours(emp.empId, emp.name, timerSecondsToHours(emp.actualSeconds))
                        }
                    }

                    val totalEmpSecs = order.assignedEmployees.sumOf { it.actualSeconds }
                    viewModel.updateSalesOrderTimer(order.soNumber, totalEmpSecs, order.status)
                }
            }
        }
    }

    // Expanded Cards State
    var expandedSoNumbers by remember { mutableStateOf(emptySet<String>()) }

    // Search, Filters, and Sorting State
    var searchQuery by remember { mutableStateOf("") }
    var selectedStatusFilter by remember { mutableStateOf("All") }
    var selectedDeptFilter by remember { mutableStateOf("All") }
    var selectedCategoryFilter by remember { mutableStateOf("All") }
    var selectedSortOrder by remember { mutableStateOf("Newest First") }

    var showDeptMenu by remember { mutableStateOf(false) }
    var showCatMenu by remember { mutableStateOf(false) }
    var showSortMenu by remember { mutableStateOf(false) }

    // Bottom Sheet & Dialog States
    var showAddEmpSheetForSo by remember { mutableStateOf<SalesOrderSupervisorCard?>(null) }
    // Retained only for state compatibility; no edit control is exposed in the UI.
    var showManualTimerDialogForSo by remember { mutableStateOf<SalesOrderSupervisorCard?>(null) }
    var showTransferSheetData by remember { mutableStateOf<Pair<SupervisorEmployeeItem, String>?>(null) } // Pair(Emp, SourceSoNumber)

    // Dialog State
    var showRemoveDialogData by remember { mutableStateOf<Pair<SupervisorEmployeeItem, String>?>(null) } // Pair(Emp, SoNumber)

    // Filtered and Sorted Sales Orders
    val filteredOrders = ordersList.filter { order: SalesOrderSupervisorCard ->
        val matchesSearch = searchQuery.isBlank() ||
                order.soNumber.contains(searchQuery, ignoreCase = true) ||
                order.projectName.contains(searchQuery, ignoreCase = true) ||
                order.customerName.contains(searchQuery, ignoreCase = true)

        val matchesStatus = selectedStatusFilter == "All" || order.status.equals(selectedStatusFilter, ignoreCase = true)
        val matchesDept = selectedDeptFilter == "All" || order.department.equals(selectedDeptFilter, ignoreCase = true)
        val matchesCat = selectedCategoryFilter == "All" || order.category.equals(selectedCategoryFilter, ignoreCase = true)

        matchesSearch && matchesStatus && matchesDept && matchesCat
    }.sortedWith(
        Comparator { a: SalesOrderSupervisorCard, b: SalesOrderSupervisorCard ->
            when (selectedSortOrder) {
                "Oldest First" -> ordersList.indexOf(a).compareTo(ordersList.indexOf(b))
                "Highest Progress" -> b.timerSeconds.compareTo(a.timerSeconds)
                "Lowest Progress" -> a.timerSeconds.compareTo(b.timerSeconds)
                else -> ordersList.indexOf(b).compareTo(ordersList.indexOf(a)) // Newest First
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(glassBackground(glassThemeStyle))
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Page Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFEDE9FE)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Supervisor",
                    tint = Color(0xFF2E1065),
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "Supervisor App",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E1065)
                )
                Text(
                    text = "Independent Sales Order Timers & Labour Allocation",
                    fontSize = 12.sp,
                    color = Color(0xFF5B3A75)
                )
            }
        }

        // Search & Filter Panel
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.66f)),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color(0xFFD8B4FE))
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Search Input
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search by SO Number, Project Name, Customer...", fontSize = 12.sp) },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color(0xFF6D28D9)) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Filled.Close, contentDescription = "Clear", tint = Color(0xFF6B4A7D))
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF6D28D9),
                        unfocusedBorderColor = Color(0xFFD8B4FE),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    singleLine = true
                )

                // Status Filter Chips Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val statusOptions = listOf("All", "Running", "Completed", "Not Started")
                    statusOptions.forEach { st ->
                        val isSel = selectedStatusFilter == st
                        FilterChip(
                            selected = isSel,
                            onClick = { selectedStatusFilter = st },
                            label = { Text(st, fontSize = 11.sp, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFFEDE9FE),
                                selectedLabelColor = Color(0xFF2E1065),
                                containerColor = Color.White.copy(alpha = 0.72f),
                                labelColor = Color(0xFF5B3A75)
                            )
                        )
                    }
                }

                // Dropdown Filters Row (Department, Category, Sorting)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Department Dropdown
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedButton(
                            onClick = { showDeptMenu = true },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                            border = BorderStroke(1.dp, Color(0xFFD8B4FE))
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Dept: $selectedDeptFilter", fontSize = 10.sp, color = Color(0xFF2E1065), maxLines = 1)
                                Icon(Icons.Filled.ArrowDropDown, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        }
                        DropdownMenu(
                            expanded = showDeptMenu,
                            onDismissRequest = { showDeptMenu = false }
                        ) {
                            val depts = (listOf("All") + departmentsList.map { it.name }).distinct()
                            depts.forEach { d ->
                                DropdownMenuItem(
                                    text = { Text(d, fontSize = 12.sp) },
                                    onClick = {
                                        selectedDeptFilter = d
                                        showDeptMenu = false
                                    }
                                )
                            }
                        }
                    }

                    // Category Dropdown
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedButton(
                            onClick = { showCatMenu = true },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                            border = BorderStroke(1.dp, Color(0xFFD8B4FE))
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Cat: $selectedCategoryFilter", fontSize = 10.sp, color = Color(0xFF2E1065), maxLines = 1)
                                Icon(Icons.Filled.ArrowDropDown, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        }
                        DropdownMenu(
                            expanded = showCatMenu,
                            onDismissRequest = { showCatMenu = false }
                        ) {
                            val cats = (listOf("All") + categoriesList.map { it.name } + masterEmployeePool.map { it.category }).distinct()
                            cats.forEach { c ->
                                DropdownMenuItem(
                                    text = { Text(c, fontSize = 12.sp) },
                                    onClick = {
                                        selectedCategoryFilter = c
                                        showCatMenu = false
                                    }
                                )
                            }
                        }
                    }

                    // Sort Order Dropdown
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedButton(
                            onClick = { showSortMenu = true },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                            border = BorderStroke(1.dp, Color(0xFFD8B4FE))
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Sort: $selectedSortOrder", fontSize = 10.sp, color = Color(0xFF2E1065), maxLines = 1)
                                Icon(Icons.Filled.ArrowDropDown, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        }
                        DropdownMenu(
                            expanded = showSortMenu,
                            onDismissRequest = { showSortMenu = false }
                        ) {
                            val sorts = listOf("Newest First", "Oldest First", "Highest Progress", "Lowest Progress")
                            sorts.forEach { s ->
                                DropdownMenuItem(
                                    text = { Text(s, fontSize = 12.sp) },
                                    onClick = {
                                        selectedSortOrder = s
                                        showSortMenu = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section Title
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "SALES ORDERS (${filteredOrders.size})",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6D28D9),
                letterSpacing = 1.sp
            )
            if (searchQuery.isNotEmpty() || selectedStatusFilter != "All" || selectedDeptFilter != "All" || selectedCategoryFilter != "All") {
                TextButton(onClick = {
                    searchQuery = ""
                    selectedStatusFilter = "All"
                    selectedDeptFilter = "All"
                    selectedCategoryFilter = "All"
                }) {
                    Text("Reset Filters", fontSize = 11.sp, color = Color(0xFF6D28D9))
                }
            }
        }

        // Empty State if no orders match
        if (filteredOrders.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.66f)),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color(0xFFD8B4FE))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = null,
                        tint = Color(0xFF6B4A7D),
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No Sales Orders Found",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color(0xFF2E1065)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "No orders match your search or filter criteria.",
                        fontSize = 12.sp,
                        color = Color(0xFF5B3A75),
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            // List of Sales Order Expandable Cards
            for (order in filteredOrders) {
                val isExpanded = expandedSoNumbers.contains(order.soNumber)

                // Render tickState to observe dynamic ticking
                val currentFormattedTimer = formatHHMMSS(order.timerSeconds + (tickState * 0))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.66f)),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(if (isExpanded) 1.5.dp else 1.dp, if (isExpanded) Color(0xFF6D28D9) else Color(0xFFD8B4FE))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        // Top Header Row of Card: SO Number, Status Chip, Expand Arrow
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = order.soNumber,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF6D28D9)
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                // Status Badge Chip
                                val (badgeBg, badgeFg) = when (order.status) {
                                    "Running" -> Color(0xFFE8F5E9) to Color(0xFF2E7D32)
                                    "Paused" -> Color(0xFFFEF3C7) to Color(0xFFF59E0B)
                                    "Completed" -> Color(0xFFE1F5FE) to Color(0xFFA855F7)
                                    "Delayed" -> Color(0xFFFFEBEE) to Color(0xFFB00020)
                                    else -> Color(0xFFEEEEEE) to Color(0xFF616161)
                                }

                                Surface(
                                    color = badgeBg,
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text(
                                        text = order.status,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = badgeFg,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                            }

                            IconButton(
                                onClick = {
                                    expandedSoNumbers = if (isExpanded) {
                                        expandedSoNumbers - order.soNumber
                                    } else {
                                        expandedSoNumbers + order.soNumber
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if (isExpanded) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowDown,
                                    contentDescription = "Expand",
                                    tint = Color(0xFF5B3A75),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        // Project & Customer Info
                        Text(
                            text = "Project : ${order.projectName}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E1065)
                        )
                        Text(
                            text = "Customer : ${order.customerName}",
                            fontSize = 12.sp,
                            color = Color(0xFF5B3A75)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Key Stats Grid
                        val recordedEmployeeSeconds = order.assignedEmployees.sumOf { it.actualSeconds.coerceAtLeast(0L) }
                        val actualSeconds = if (recordedEmployeeSeconds > 0L) recordedEmployeeSeconds else order.timerSeconds.coerceAtLeast(0L)
                        val actualCost = if (recordedEmployeeSeconds > 0L) {
                            order.assignedEmployees.sumOf {
                                (it.actualSeconds.coerceAtLeast(0L) / 3600.0) * it.hourlyRate.coerceAtLeast(0.0)
                            }
                        } else {
                            val averageRate = order.assignedEmployees
                                .map { it.hourlyRate.coerceAtLeast(0.0) }
                                .takeIf { it.isNotEmpty() }
                                ?.average()
                                ?: 0.0
                            (actualSeconds / 3600.0) * averageRate
                        }
                        val actualHours = timerSecondsToHours(actualSeconds)
                        val hoursExceeded = order.plannedHours > 0 && actualHours > order.plannedHours.toDouble()
                        val costExceeded = order.plannedBudget > 0.0 && actualCost > order.plannedBudget
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Employees", fontSize = 10.sp, color = Color(0xFF6B4A7D), fontWeight = FontWeight.Bold, maxLines = 2, minLines = 2, overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center)
                                Text("${order.assignedEmployees.size}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E1065), maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Planned Hours", fontSize = 10.sp, color = Color(0xFF6B4A7D), fontWeight = FontWeight.Bold, maxLines = 2, minLines = 2, overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center)
                                Text("${order.plannedHours} hrs", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E1065), maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Actual Hours", fontSize = 10.sp, color = Color(0xFF6B4A7D), fontWeight = FontWeight.Bold, maxLines = 2, minLines = 2, overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center)
                                Text(formatHHMMSS(actualSeconds), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = if (hoursExceeded) Color(0xFFB91C1C) else Color(0xFF2E7D32), maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Actual Cost", fontSize = 10.sp, color = Color(0xFF6B4A7D), fontWeight = FontWeight.Bold, maxLines = 2, minLines = 2, overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center)
                                Text(formatCostValue(actualCost), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = if (costExceeded) Color(0xFFB91C1C) else Color(0xFF2E7D32), maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Department", fontSize = 10.sp, color = Color(0xFF6B4A7D), fontWeight = FontWeight.Bold, maxLines = 2, minLines = 2, overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center)
                                Text(order.department, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6D28D9), maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                        }

                        if (hoursExceeded || costExceeded) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFFFE4E6), RoundedCornerShape(10.dp))
                                    .border(1.dp, Color(0xFFFB7185), RoundedCornerShape(10.dp))
                                    .padding(12.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Warning,
                                    contentDescription = "Plan exceeded warning",
                                    tint = Color(0xFFBE123C),
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                    Text("PLAN EXCEEDED", color = Color(0xFF9F1239), fontSize = 11.sp, fontWeight = FontWeight.ExtraBold)
                                    if (hoursExceeded) {
                                        Text(
                                            "Actual hours exceed plan by ${formatSavedHours(actualHours - order.plannedHours)} h.",
                                            color = Color(0xFF881337), fontSize = 11.sp, fontWeight = FontWeight.Medium
                                        )
                                    }
                                    if (costExceeded) {
                                        Text(
                                            "Actual cost exceeds budget by ${formatCostValue(actualCost - order.plannedBudget)}.",
                                            color = Color(0xFF881337), fontSize = 11.sp, fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // DIGITAL TIMER & CONTROLS BOX
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFF3E8FF), RoundedCornerShape(12.dp))
                                .border(1.dp, Color(0xFFD8B4FE), RoundedCornerShape(12.dp))
                                .padding(14.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = "Sales Order Timer",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF6B4A7D),
                                    letterSpacing = 0.5.sp
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = currentFormattedTimer,
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = if (order.isTimerRunning) Color(0xFF2E7D32) else Color(0xFF2E1065),
                                    letterSpacing = 1.sp
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                // Single Unified Timer Action Button (Start / Stop & Save Timer)
                                Button(
                                    onClick = {
                                        if (order.isTimerRunning) {
                                            // STOP & SAVE TIMER
                                            val stoppedAt = currentFormattedTimer
                                            order.isTimerRunning = false
                                            order.status = "Paused"
                                            order.timerSeconds = 0L
                                            viewModel.updateSalesOrderTimer(order.soNumber, 0L, "Paused")
                                            for (emp in order.assignedEmployees) {
                                                val key = "${order.soNumber}_${emp.empId}"
                                                if (selectedEmployeeKeys.contains(key)) {
                                                    viewModel.updateEmployeeHours(emp.empId, emp.name, timerSecondsToHours(emp.actualSeconds))
                                                }
                                            }
                                            val timestamp = currentIsoTimestamp()
                                            viewModel.addLog("""{"event": "STOP_RESET_TIMER", "salesOrderId": "${order.soNumber}", "time": "$stoppedAt", "timestamp": "$timestamp"}""", "INFO")
                                            val savedHours = order.assignedEmployees
                                                .filter { selectedEmployeeKeys.contains("${order.soNumber}_${it.empId}") }
                                                .sumOf { timerSecondsToHours(it.actualSeconds) }
                                            Toast.makeText(context, "Saved $stoppedAt as ${formatSavedHours(savedHours)} labour hours.", Toast.LENGTH_LONG).show()
                                        } else {
                                            // START & SAVE TIMER
                                            order.isTimerRunning = true
                                            order.status = "Running"
                                            viewModel.updateSalesOrderTimer(order.soNumber, order.timerSeconds, "Running")
                                            for (emp in order.assignedEmployees) {
                                                val key = "${order.soNumber}_${emp.empId}"
                                                if (selectedEmployeeKeys.contains(key)) {
                                                    viewModel.updateEmployeeHours(emp.empId, emp.name, timerSecondsToHours(emp.actualSeconds))
                                                }
                                            }
                                            val timestamp = currentIsoTimestamp()
                                            viewModel.addLog("""{"event": "START_SAVE_TIMER", "salesOrderId": "${order.soNumber}", "timestamp": "$timestamp"}""", "SUCCESS")
                                            Toast.makeText(context, "Timer Started & Saved for ${order.soNumber}!", Toast.LENGTH_SHORT).show()
                                        }
                                    },
                                    enabled = order.status != "Completed",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(40.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (order.isTimerRunning) Color(0xFFB00020) else Color(0xFF6D28D9),
                                        contentColor = Color.White,
                                        disabledContainerColor = Color(0xFFE0E0E0),
                                        disabledContentColor = Color(0xFF9E9E9E)
                                    ),
                                    shape = RoundedCornerShape(10.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Icon(
                                        imageVector = if (order.isTimerRunning) Icons.Filled.Close else Icons.Filled.PlayArrow,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (order.isTimerRunning) "Stop & Save Timer" else "Start & Save Timer",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        // EXPANDED EMPLOYEES SECTION
                        if (isExpanded) {
                            Spacer(modifier = Modifier.height(16.dp))
                            HorizontalDivider(color = Color(0xFFD8B4FE))
                            Spacer(modifier = Modifier.height(12.dp))

                            // Sub-Header: Employees & Selection Checkbox & Add Employee Button
                            val currentSoEmpKeys = order.assignedEmployees.map { "${order.soNumber}_${it.empId}" }
                            val allEmpsInSoSelected = currentSoEmpKeys.isNotEmpty() && currentSoEmpKeys.all { selectedEmployeeKeys.contains(it) }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    if (order.assignedEmployees.isNotEmpty()) {
                                        Checkbox(
                                            checked = allEmpsInSoSelected,
                                            onCheckedChange = { checked ->
                                                if (checked) {
                                                    for (key in currentSoEmpKeys) {
                                                        if (!selectedEmployeeKeys.contains(key)) selectedEmployeeKeys.add(key)
                                                    }
                                                } else {
                                                    for (key in currentSoEmpKeys) {
                                                        selectedEmployeeKeys.remove(key)
                                                    }
                                                }
                                            },
                                            colors = CheckboxDefaults.colors(checkedColor = Color(0xFF6D28D9))
                                        )
                                        Spacer(modifier = Modifier.width(2.dp))
                                    }
                                    Text(
                                        text = "ASSIGNED EMPLOYEES (${order.assignedEmployees.size})",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF2E1065)
                                    )
                                }

                                Button(
                                    onClick = {
                                        showAddEmpSheetForSo = order
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF6D28D9),
                                        contentColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(20.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("+ Add Employee", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Employee List Cards
                            if (order.assignedEmployees.isEmpty()) {
                                Text(
                                    text = "No employees assigned to this Sales Order yet. Tap '+ Add Employee' to assign.",
                                    fontSize = 12.sp,
                                    color = Color(0xFF6B4A7D),
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            } else {
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    for (emp in order.assignedEmployees) {
                                        val empKey = "${order.soNumber}_${emp.empId}"
                                        val isEmpSelected = selectedEmployeeKeys.contains(empKey)

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Checkbox(
                                                checked = isEmpSelected,
                                                onCheckedChange = { checked ->
                                                    if (checked) {
                                                        if (!selectedEmployeeKeys.contains(empKey)) selectedEmployeeKeys.add(empKey)
                                                    } else {
                                                        selectedEmployeeKeys.remove(empKey)
                                                    }
                                                },
                                                colors = CheckboxDefaults.colors(
                                                    checkedColor = Color(0xFF6D28D9),
                                                    uncheckedColor = Color(0xFF6B4A7D)
                                                )
                                            )

                                            Card(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .clickable {
                                                        if (isEmpSelected) {
                                                            selectedEmployeeKeys.remove(empKey)
                                                        } else {
                                                            selectedEmployeeKeys.add(empKey)
                                                        }
                                                    },
                                                colors = CardDefaults.cardColors(
                                                    containerColor = if (isEmpSelected) {
                                                        Color(0xFFF3E8FF)
                                                    } else {
                                                        Color(0xFFF8F9FA)
                                                    }
                                                ),
                                                border = BorderStroke(
                                                    if (isEmpSelected) 1.5.dp else 1.dp,
                                                    if (isEmpSelected) Color(0xFF6D28D9) else Color(0xFFD8B4FE)
                                                ),
                                                shape = RoundedCornerShape(10.dp)
                                            ) {
                                                Column(modifier = Modifier.padding(12.dp)) {
                                                    // Employee Name & Skill
                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                                            Text(
                                                                text = emp.name,
                                                                fontSize = 14.sp,
                                                                fontWeight = FontWeight.Bold,
                                                                color = Color(0xFF2E1065)
                                                            )
                                                            Spacer(modifier = Modifier.width(6.dp))
                                                            Text(
                                                                text = "(${emp.empId})",
                                                                fontSize = 11.sp,
                                                                color = Color(0xFF6D28D9),
                                                                fontWeight = FontWeight.SemiBold
                                                            )
                                                        }

                                                        Surface(
                                                            color = Color(0xFFE2F1E7),
                                                            shape = RoundedCornerShape(8.dp)
                                                        ) {
                                                            Text(
                                                                text = emp.skillLevel,
                                                                fontSize = 10.sp,
                                                                fontWeight = FontWeight.Bold,
                                                                color = Color(0xFF1B5E20),
                                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                            )
                                                        }
                                                    }

                                                    Spacer(modifier = Modifier.height(4.dp))

                                                    // Details: Category • Dept & Actual Hr
                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Text(
                                                            text = "${emp.category} • Dept: ${emp.department}",
                                                            fontSize = 11.sp,
                                                            color = Color(0xFF5B3A75)
                                                        )
                                                        val empTimeFormatted = formatHHMMSS(emp.actualSeconds)
                                                        Text(
                                                            text = "Actual Hr: $empTimeFormatted",
                                                            fontSize = 11.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color(0xFF2E7D32)
                                                        )
                                                    }

                                                    Spacer(modifier = Modifier.height(10.dp))

                                                    // Action Buttons: Transfer & Remove
                                                    Row(
                                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                                        modifier = Modifier.align(Alignment.End)
                                                    ) {
                                                        OutlinedButton(
                                                            onClick = {
                                                                showTransferSheetData = Pair(emp, order.soNumber)
                                                            },
                                                            shape = RoundedCornerShape(8.dp),
                                                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                                            border = BorderStroke(1.dp, Color(0xFF6D28D9))
                                                        ) {
                                                            Icon(Icons.Filled.SwapHoriz, contentDescription = "Transfer", modifier = Modifier.size(14.dp), tint = Color(0xFF6D28D9))
                                                            Spacer(modifier = Modifier.width(4.dp))
                                                            Text("Transfer", fontSize = 11.sp, color = Color(0xFF6D28D9), fontWeight = FontWeight.Bold)
                                                        }

                                                        OutlinedButton(
                                                            onClick = {
                                                                showRemoveDialogData = Pair(emp, order.soNumber)
                                                            },
                                                            shape = RoundedCornerShape(8.dp),
                                                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                                            border = BorderStroke(1.dp, Color(0xFFB00020))
                                                        ) {
                                                            Icon(Icons.Filled.Delete, contentDescription = "Remove", modifier = Modifier.size(14.dp), tint = Color(0xFFB00020))
                                                            Spacer(modifier = Modifier.width(4.dp))
                                                            Text("Remove", fontSize = 11.sp, color = Color(0xFFB00020), fontWeight = FontWeight.Bold)
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // -------------------------------------------------------------------------
    // BOTTOM SHEET: ADD EMPLOYEE TO SALES ORDER
    // -------------------------------------------------------------------------
    showAddEmpSheetForSo?.let { targetSo ->
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val currentAssignedIds = remember(targetSo.assignedEmployees.size) {
            targetSo.assignedEmployees.map { it.empId }.toSet()
        }
        val availableToAssign = remember(currentAssignedIds) {
            masterEmployeePool.filter { it.empId !in currentAssignedIds }
        }
        var selectedIdsToAssign by remember { mutableStateOf(setOf<String>()) }

        ModalBottomSheet(
            onDismissRequest = { showAddEmpSheetForSo = null },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Add Employees to ${targetSo.soNumber}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E1065)
                )
                Text(
                    text = "Select one or more available employees to assign. The Sales Order timer will continue running without interruption.",
                    fontSize = 12.sp,
                    color = Color(0xFF5B3A75)
                )

                if (availableToAssign.isEmpty()) {
                    Text(
                        text = "All company employees are currently assigned to this Sales Order.",
                        fontSize = 12.sp,
                        color = Color(0xFF6B4A7D),
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (emp in availableToAssign) {
                            val isChecked = selectedIdsToAssign.contains(emp.empId)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isChecked) Color(0xFFF3E8FF) else Color(0xFFF8F9FA))
                                    .clickable {
                                        selectedIdsToAssign = if (isChecked) {
                                            selectedIdsToAssign - emp.empId
                                        } else {
                                            selectedIdsToAssign + emp.empId
                                        }
                                    }
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = isChecked,
                                    onCheckedChange = { checked ->
                                        selectedIdsToAssign = if (checked) {
                                            selectedIdsToAssign + emp.empId
                                        } else {
                                            selectedIdsToAssign - emp.empId
                                        }
                                    },
                                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF6D28D9))
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "${emp.name} (${emp.empId})",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = Color(0xFF2E1065)
                                    )
                                    Text(
                                        text = "${emp.category} • ${emp.skillLevel}",
                                        fontSize = 11.sp,
                                        color = Color(0xFF5B3A75)
                                    )
                                }
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = { showAddEmpSheetForSo = null }) {
                        Text("Cancel", color = Color(0xFF5B3A75))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val newEmps = availableToAssign.filter { it.empId in selectedIdsToAssign }
                            targetSo.assignedEmployees.addAll(newEmps)
                            viewModel.moveEmployees(newEmps.map { it.empId }, targetSo.soNumber)
                            viewModel.addLog("[ASSIGN] Added ${newEmps.size} employees to ${targetSo.soNumber}", "SUCCESS")
                            showAddEmpSheetForSo = null
                        },
                        enabled = selectedIdsToAssign.isNotEmpty(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6D28D9)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Assign Selected (${selectedIdsToAssign.size})", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    // -------------------------------------------------------------------------
    // BOTTOM SHEET: TRANSFER / MOVE EMPLOYEE
    // -------------------------------------------------------------------------
    showTransferSheetData?.let { (emp, sourceSoNumber) ->
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val destinationOrders = remember {
            ordersList.filter { it.soNumber != sourceSoNumber }
        }
        var selectedDestSoNumber by remember {
            mutableStateOf(destinationOrders.firstOrNull()?.soNumber ?: "")
        }

        ModalBottomSheet(
            onDismissRequest = { showTransferSheetData = null },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Move Employee",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E1065)
                )

                Text(
                    text = "Transfer ${emp.name} (${emp.empId}) from $sourceSoNumber to another Sales Order. Timers of both Sales Orders will remain unchanged.",
                    fontSize = 12.sp,
                    color = Color(0xFF5B3A75)
                )

                // Current Source Card
                Surface(
                    color = Color(0xFFF3E8FF),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Current: ", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF5B3A75))
                        Text(sourceSoNumber, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6D28D9))
                    }
                }

                Text("Select Destination Sales Order:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E1065))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (destSo in destinationOrders) {
                        val isSelected = selectedDestSoNumber == destSo.soNumber
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) Color(0xFFEDE9FE) else Color(0xFFF8F9FA))
                                .clickable { selectedDestSoNumber = destSo.soNumber }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { selectedDestSoNumber = destSo.soNumber },
                                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF6D28D9))
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("${destSo.soNumber} — ${destSo.projectName}", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF2E1065))
                                Text("Customer: ${destSo.customerName} • Status: ${destSo.status}", fontSize = 11.sp, color = Color(0xFF5B3A75))
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { showTransferSheetData = null }) {
                        Text("Cancel", color = Color(0xFF5B3A75))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val sourceSo = ordersList.find { it.soNumber == sourceSoNumber }
                            val destSo = ordersList.find { it.soNumber == selectedDestSoNumber }

                            if (sourceSo != null && destSo != null) {
                                sourceSo.assignedEmployees.removeIf { it.empId == emp.empId }
                                if (destSo.assignedEmployees.none { it.empId == emp.empId }) {
                                    destSo.assignedEmployees.add(emp)
                                }
                                viewModel.moveEmployees(listOf(emp.empId), destSo.soNumber)
                                viewModel.addLog("[TRANSFER] Moved ${emp.name} from $sourceSoNumber to ${destSo.soNumber}", "INFO")
                            }
                            showTransferSheetData = null
                        },
                        enabled = selectedDestSoNumber.isNotEmpty(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6D28D9)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Confirm Move", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    // -------------------------------------------------------------------------
    // CONFIRMATION DIALOG: REMOVE EMPLOYEE
    // -------------------------------------------------------------------------
    showRemoveDialogData?.let { (emp, soNumber) ->
        AlertDialog(
            onDismissRequest = { showRemoveDialogData = null },
            title = {
                Text("Remove Employee?", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            },
            text = {
                Text(
                    "Are you sure you want to remove ${emp.name} (${emp.empId}) from Sales Order $soNumber? Removing an employee will not stop the Sales Order timer.",
                    fontSize = 12.sp,
                    color = Color(0xFF5B3A75)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        val targetSo = ordersList.find { it.soNumber == soNumber }
                        targetSo?.assignedEmployees?.removeIf { it.empId == emp.empId }
                        viewModel.moveEmployees(listOf(emp.empId), "Unassigned")
                        viewModel.addLog("[REMOVE] Removed ${emp.name} from $soNumber", "WARNING")
                        showRemoveDialogData = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB00020))
                ) {
                    Text("Remove", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showRemoveDialogData = null }) {
                    Text("Cancel", color = Color(0xFF6D28D9))
                }
            },
            containerColor = Color.White
        )
    }

    // -------------------------------------------------------------------------
    // DIALOG: MANUAL TIMER EDIT & SYNC
    // -------------------------------------------------------------------------
    showManualTimerDialogForSo?.let { targetSo ->
        var hoursInput by remember { mutableStateOf((targetSo.timerSeconds / 3600).toString()) }
        var minutesInput by remember { mutableStateOf(((targetSo.timerSeconds % 3600) / 60).toString()) }
        var secondsInput by remember { mutableStateOf((targetSo.timerSeconds % 60).toString()) }

        AlertDialog(
            onDismissRequest = { showManualTimerDialogForSo = null },
            title = {
                Text("Edit & Save Timer — ${targetSo.soNumber}", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF2E1065))
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        "Set the exact timer duration for ${targetSo.soNumber}. Saving will update this timer across the entire application (Home KPIs, Operator Tab, Reports, and Details).",
                        fontSize = 12.sp,
                        color = Color(0xFF5B3A75)
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = hoursInput,
                            onValueChange = { hoursInput = it },
                            label = { Text("Hours") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6D28D9))
                        )
                        OutlinedTextField(
                            value = minutesInput,
                            onValueChange = { minutesInput = it },
                            label = { Text("Mins") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6D28D9))
                        )
                        OutlinedTextField(
                            value = secondsInput,
                            onValueChange = { secondsInput = it },
                            label = { Text("Secs") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6D28D9))
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val h = hoursInput.toLongOrNull() ?: 0L
                        val m = minutesInput.toLongOrNull() ?: 0L
                        val s = secondsInput.toLongOrNull() ?: 0L
                        val newTotalSecs = (h * 3600) + (m * 60) + s
                        targetSo.timerSeconds = newTotalSecs

                        viewModel.updateSalesOrderTimer(targetSo.soNumber, newTotalSecs, targetSo.status)

                        if (targetSo.assignedEmployees.isNotEmpty()) {
                            val perEmpSecs = newTotalSecs / targetSo.assignedEmployees.size
                            for (emp in targetSo.assignedEmployees) {
                                emp.actualSeconds = perEmpSecs
                                val perEmployeeHours = newTotalSecs.toDouble() / targetSo.assignedEmployees.size / 3600.0
                                viewModel.updateEmployeeHours(emp.empId, emp.name, perEmployeeHours)
                            }
                        }

                        val timestamp = currentIsoTimestamp()
                        viewModel.addLog("""{"event": "MANUAL_EDIT_TIMER", "salesOrderId": "${targetSo.soNumber}", "seconds": $newTotalSecs, "timestamp": "$timestamp"}""", "SUCCESS")
                        Toast.makeText(context, "Saved ${formatHHMMSS(newTotalSecs)} as ${formatSavedHours(timerSecondsToHours(newTotalSecs))} hours.", Toast.LENGTH_LONG).show()
                        showManualTimerDialogForSo = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6D28D9))
                ) {
                    Text("Save & Sync", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showManualTimerDialogForSo = null }) {
                    Text("Cancel", color = Color(0xFF6D28D9))
                }
            },
            containerColor = Color.White
        )
    }
}

@Composable
fun ReportTab(
    innerPadding: PaddingValues,
    viewModel: DashboardViewModel,
    salesOrders: List<SalesOrder>,
    employees: List<EmployeeActivity>,
    completedJobsCount: Int,
    totalJobsCount: Int,
    averageProductivity: Double,
    totalClockedHours: Double,
    glassThemeStyle: GlassThemeStyle = GlassThemeStyle.ROYAL_GLASS
) {
    val context = LocalContext.current
    var selectedTemplate by remember { mutableStateOf("Sales Order Wise Labour Report") }
    var selectedSoFilter by remember { mutableStateOf("All Orders") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var showExportCard by remember { mutableStateOf(false) }
    var lastExportMessage by remember { mutableStateOf<String?>(null) }

    val totalPlannedHours = remember(salesOrders) { salesOrders.sumOf { getPlannedHrForOrder(it) } }
    val totalActualManhours = remember(salesOrders, employees) { salesOrders.sumOf { getActualHrForOrder(it, employees) } }
    val totalLabourCostSum = remember(salesOrders, employees) { salesOrders.sumOf { getActualCostForOrder(it, employees) } }
    val exportScope = rememberCoroutineScope()
    val exportReport: (String) -> Unit = { format ->
        val safeTemplateName = selectedTemplate
            .replace(Regex("[^A-Za-z0-9]+"), "_")
            .trim('_')
        val extension = when (format) {
            "PDF" -> ".pdf"
            "WORD" -> ".docx"
            "EXCEL" -> ".xlsx"
            "CSV" -> ".csv"
            else -> error("Unsupported export format")
        }
        val fileName = "${safeTemplateName}_${System.currentTimeMillis()}$extension"
        val reportRange =
            if (startDate.isNotBlank() && endDate.isNotBlank()) "$startDate to $endDate"
            else "All Time"

        exportScope.launch {
            val result = withContext(Dispatchers.IO) {
                when (format) {
                    "PDF" -> ReportExporter.exportPdf(
                        context,
                        fileName,
                        selectedTemplate,
                        selectedSoFilter,
                        reportRange,
                        salesOrders,
                        employees
                    )
                    "WORD" -> ReportExporter.exportDocx(
                        context,
                        fileName,
                        selectedTemplate,
                        selectedSoFilter,
                        reportRange,
                        salesOrders,
                        employees
                    )
                    "EXCEL" -> ReportExporter.exportXlsx(
                        context,
                        fileName,
                        selectedTemplate,
                        selectedSoFilter,
                        reportRange,
                        salesOrders,
                        employees
                    )
                    "CSV" -> ReportExporter.exportCsv(
                        context,
                        fileName,
                        selectedTemplate,
                        selectedSoFilter,
                        reportRange,
                        salesOrders,
                        employees
                    )
                    else -> error("Unsupported export format")
                }
            }
            result.onSuccess { savedPath ->
                lastExportMessage = "Saved: $savedPath"
                viewModel.addLog(
                    "[EXPORTS] Watermarked $format report saved: $fileName",
                    "SUCCESS"
                )
                Toast.makeText(
                    context,
                    "$format report saved to $savedPath",
                    Toast.LENGTH_LONG
                ).show()
            }.onFailure { error ->
                Toast.makeText(
                    context,
                    "Unable to export report: ${error.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    val printReport: () -> Unit = {
        val reportRange =
            if (startDate.isNotBlank() && endDate.isNotBlank()) "$startDate to $endDate"
            else "All Time"
        val result = ReportExporter.printPdf(
            context,
            selectedTemplate,
            selectedTemplate,
            selectedSoFilter,
            reportRange,
            salesOrders,
            employees
        )
        result.onFailure { error ->
            Toast.makeText(
                context,
                "Unable to open print preview: ${error.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(glassBackground(glassThemeStyle))
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Page Title & Subtitle Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF6D28D9).copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ShowChart,
                    contentDescription = "Labour Costing Logo",
                    tint = Color(0xFF6D28D9),
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Real-Time Labour Costing",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E1065)
                )
                Text(
                    text = "Manufacturing Floor Resource Utilization & Tracking System",
                    fontSize = 11.sp,
                    color = Color(0xFF5B3A75)
                )
            }
        }

        // Summary KPI Metrics Row (2 Cards per Row)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Planned Manhours KPI
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.66f)),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.9f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 7.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("PLANNED MANHOURS", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF5B3A75), letterSpacing = 0.5.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("${String.format(Locale.US, "%.1f", totalPlannedHours)} hrs", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF6D28D9))
                }
            }

            // Actual Clocked Manhours KPI
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.66f)),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.9f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 7.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("ACTUAL MANHOURS", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF5B3A75), letterSpacing = 0.5.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("${String.format(Locale.US, "%.1f", totalActualManhours)} hrs", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFFA855F7))
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Total Labour Cost KPI
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.66f)),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.9f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 7.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("ACTUAL LABOUR COST", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF5B3A75), letterSpacing = 0.5.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(formatCostValue(totalLabourCostSum), fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF2E7D32))
                }
            }

            // Overall Efficiency KPI
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.66f)),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.9f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 7.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("PLANT EFFICIENCY", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF5B3A75), letterSpacing = 0.5.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("${String.format(Locale.US, "%.1f", averageProductivity)}%", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFFF59E0B))
                }
            }
        }

        // Top Filter & Controls Configuration Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.66f)),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.9f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "REPORT CONFIGURATION",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6D28D9),
                    letterSpacing = 0.8.sp
                )

                // Row 1: Report Template & Order Filter
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    LightDropdownSelection(
                        selectedValue = selectedTemplate,
                        label = "REPORT TEMPLATE",
                        options = listOf(
                            "Sales Order Wise Labour Report",
                            "Employee Wise Timesheet",
                            "Departmental Utilization Report"
                        )
                    ) { selectedTemplate = it }

                    val filterOptions = listOf("All Orders") + salesOrders.map { it.id }
                    LightDropdownSelection(
                        selectedValue = selectedSoFilter,
                        label = "FILTER SALES ORDER",
                        options = filterOptions
                    ) { selectedSoFilter = it }
                }

                // Row 2: Date Inputs
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        LightDateInputField(
                            value = startDate,
                            onValueChange = { startDate = it },
                            label = "START DATE",
                            placeholder = "mm/dd/yyyy"
                        )
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        LightDateInputField(
                            value = endDate,
                            onValueChange = { endDate = it },
                            label = "END DATE",
                            placeholder = "mm/dd/yyyy"
                        )
                    }
                }

                // Row 3: Print & Export Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            printReport()
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Color(0xFFD8B4FE)),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF2E1065)),
                        contentPadding = PaddingValues(vertical = 10.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Print, contentDescription = "Print PDF", modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Print PDF", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = {
                            showExportCard = !showExportCard
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6D28D9), contentColor = Color.White),
                        contentPadding = PaddingValues(vertical = 10.dp)
                    ) {
                        Icon(imageVector = Icons.Default.FileDownload, contentDescription = "Export Report", modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Export", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = if (showExportCard) Icons.Default.KeyboardArrowDown else Icons.Default.ArrowDropDown,
                            contentDescription = "Expand Options",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                // Expanded Export Formats Card
                AnimatedVisibility(visible = showExportCard) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                            .background(Color(0xFFF7F2FF), shape = RoundedCornerShape(10.dp))
                            .border(BorderStroke(1.dp, Color(0xFF6D28D9).copy(alpha = 0.3f)), shape = RoundedCornerShape(10.dp))
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "SELECT EXPORT FORMAT",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF6D28D9),
                                    letterSpacing = 0.5.sp
                                )
                                Text(
                                    text = "Download $selectedTemplate in preferred format",
                                    fontSize = 10.sp,
                                    color = Color(0xFF6B4A7D)
                                )
                            }
                            IconButton(
                                onClick = { showExportCard = false },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close",
                                    tint = Color(0xFF6B4A7D),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        // Grid of 4 Formats: PDF, EXCEL, WORD, CSV
                        val formats = listOf(
                            ExportFormatInfo("PDF Document", ".pdf", "PDF", Color(0xFFDC2626), Icons.Default.Print, "Formatted PDF Report"),
                            ExportFormatInfo("Excel Sheet", ".xlsx", "EXCEL", Color(0xFF16A34A), Icons.Default.TableChart, "Formulas & Worksheets"),
                            ExportFormatInfo("Word Document", ".docx", "WORD", Color(0xFF6D28D9), Icons.Default.Description, "Formatted Doc Report"),
                            ExportFormatInfo("CSV File", ".csv", "CSV", Color(0xFF9333EA), Icons.Default.FileDownload, "Comma Separated Data")
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            formats.chunked(2).forEach { rowItems ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    rowItems.forEach { item ->
                                        Surface(
                                            modifier = Modifier
                                                .weight(1f)
                                                .clickable {
                                                    when (item.badgeText) {
                                                        "PDF" -> exportReport("PDF")
                                                        "WORD" -> exportReport("WORD")
                                                        "EXCEL" -> exportReport("EXCEL")
                                                        "CSV" -> exportReport("CSV")
                                                    }
                                                },
                                            shape = RoundedCornerShape(8.dp),
                                            color = Color.White,
                                            border = BorderStroke(1.dp, item.color.copy(alpha = 0.4f))
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(10.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(32.dp)
                                                        .background(item.color.copy(alpha = 0.12f), shape = RoundedCornerShape(6.dp)),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Icon(
                                                        imageVector = item.icon,
                                                        contentDescription = item.label,
                                                        tint = item.color,
                                                        modifier = Modifier.size(18.dp)
                                                    )
                                                }
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Column(modifier = Modifier.weight(1f)) {
                                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                                        Text(
                                                            text = item.badgeText,
                                                            fontSize = 11.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = item.color
                                                        )
                                                        Spacer(modifier = Modifier.width(4.dp))
                                                        Text(
                                                            text = item.extension,
                                                            fontSize = 9.sp,
                                                            color = Color(0xFF6B4A7D)
                                                        )
                                                    }
                                                    Text(
                                                        text = item.description,
                                                        fontSize = 9.sp,
                                                        color = Color(0xFF6B4A7D),
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (lastExportMessage != null) {
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0xFFDCFCE7),
                                border = BorderStroke(1.dp, Color(0xFF86EFAC))
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Success",
                                            tint = Color(0xFF166534),
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = lastExportMessage ?: "",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color(0xFF166534),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Dismiss",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF166534),
                                        modifier = Modifier.clickable { lastExportMessage = null }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Main Report Detail Data Table Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.66f)),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color(0xFFD8B4FE))
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Table Title Header Area
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = selectedTemplate,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E1065)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Filter: $selectedSoFilter | Range: ${if (startDate.isNotBlank() && endDate.isNotBlank()) "$startDate to $endDate" else "All Time"}",
                            fontSize = 11.sp,
                            color = Color(0xFF6B4A7D),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "Generated Today",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF6D28D9)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFD8B4FE))
                )

                // Scrollable Table Content based on Template Selection
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                ) {
                    when (selectedTemplate) {
                        "Employee Wise Timesheet" -> {
                            Column {
                                // Table Header
                                Row(
                                    modifier = Modifier
                                        .background(Color(0xFFF3E8FF))
                                        .padding(horizontal = 16.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TableHeaderCell("EMP ID", 90.dp)
                                    TableHeaderCell("NAME", 140.dp)
                                    TableHeaderCell("ROLE", 120.dp)
                                    TableHeaderCell("ASSIGNMENT", 130.dp)
                                    TableHeaderCell("HOURS", 90.dp)
                                    TableHeaderCell("RATE", 90.dp)
                                    TableHeaderCell("TOTAL COST", 110.dp)
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(Color(0xFFD8B4FE))
                                )

                                employees.forEachIndexed { idx, emp ->
                                    val empId = getEmpIdByName(emp.name)
                                    val rate = emp.hourlyRate
                                    val cost = emp.hoursClocked * rate

                                    Row(
                                        modifier = Modifier
                                            .background(if (idx % 2 == 1) Color(0xFFF8F9FA) else Color.White)
                                            .padding(horizontal = 16.dp, vertical = 11.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        TableCell(empId, 90.dp, isMonospace = true, fontWeight = FontWeight.Bold)
                                        TableCell(emp.name, 140.dp, fontWeight = FontWeight.Medium)
                                        TableCell(emp.category, 120.dp, color = Color(0xFF6D28D9))
                                        TableCell(emp.task, 130.dp)
                                        TableCell("${formatSavedHours(emp.hoursClocked)} hrs", 90.dp)
                                        TableCell("${formatCostValue(rate)}/hr", 90.dp)
                                        TableCell(formatCostValue(cost), 110.dp, fontWeight = FontWeight.Bold)
                                    }

                                    if (idx < employees.size - 1) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(1.dp)
                                                .background(Color(0xFFF3E8FF))
                                        )
                                    }
                                }
                            }
                        }

                        "Departmental Utilization Report" -> {
                            Column {
                                // Table Header
                                Row(
                                    modifier = Modifier
                                        .background(Color(0xFFF3E8FF))
                                        .padding(horizontal = 16.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TableHeaderCell("DEPARTMENT", 140.dp)
                                    TableHeaderCell("ORDERS", 90.dp)
                                    TableHeaderCell("WORKERS", 90.dp)
                                    TableHeaderCell("PLANNED HRS", 110.dp)
                                    TableHeaderCell("ACTUAL HRS", 110.dp)
                                    TableHeaderCell("LABOUR COST", 110.dp)
                                    TableHeaderCell("EFFICIENCY", 100.dp)
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(Color(0xFFD8B4FE))
                                )

                                val depts = listOf("Assembly", "Machining", "Quality", "Packing")
                                depts.forEachIndexed { idx, dept ->
                                    val deptOrders = salesOrders.filter { it.department.equals(dept, ignoreCase = true) }
                                    val deptEmps = employees.filter { emp ->
                                        deptOrders.any { it.id == emp.task } || emp.category.contains(dept, ignoreCase = true)
                                    }
                                    val plannedHr = deptOrders.sumOf { getPlannedHrForOrder(it) }
                                    val actualHr = deptOrders.sumOf { getActualHrForOrder(it, employees) }
                                    val cost = deptOrders.sumOf { getActualCostForOrder(it, employees) }
                                    val eff = if (actualHr > 0) (plannedHr / actualHr * 100.0).coerceAtMost(100.0) else 100.0

                                    Row(
                                        modifier = Modifier
                                            .background(if (idx % 2 == 1) Color(0xFFF8F9FA) else Color.White)
                                            .padding(horizontal = 16.dp, vertical = 11.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        TableCell(dept, 140.dp, fontWeight = FontWeight.Bold, color = Color(0xFF6D28D9))
                                        TableCell("${deptOrders.size} SOs", 90.dp)
                                        TableCell("${deptEmps.size} workers", 90.dp)
                                        TableCell("${String.format(Locale.US, "%.1f", plannedHr)} hrs", 110.dp)
                                        TableCell("${String.format(Locale.US, "%.1f", actualHr)} hrs", 110.dp)
                                        TableCell(formatCostValue(cost), 110.dp, fontWeight = FontWeight.Bold)
                                        TableCell("${String.format(Locale.US, "%.1f", eff)}%", 100.dp, color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                                    }

                                    if (idx < depts.size - 1) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(1.dp)
                                                .background(Color(0xFFF3E8FF))
                                        )
                                    }
                                }
                            }
                        }

                        else -> {
                            // "Sales Order Wise Labour Report" (Default)
                            Column {
                                // Table Header
                                Row(
                                    modifier = Modifier
                                        .background(Color(0xFFF3E8FF))
                                        .padding(horizontal = 16.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TableHeaderCell("SO ID", 110.dp)
                                    TableHeaderCell("CUSTOMER", 160.dp)
                                    TableHeaderCell("CATEGORY", 140.dp)
                                    TableHeaderCell("PERSONNEL", 100.dp)
                                    TableHeaderCell("PLANNED HRS", 110.dp)
                                    TableHeaderCell("ACTUAL HRS", 120.dp)
                                    TableHeaderCell("LABOUR COST", 110.dp)
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(Color(0xFFD8B4FE))
                                )

                                val filteredOrders = remember(salesOrders, selectedSoFilter) {
                                    if (selectedSoFilter != "All Orders") {
                                        salesOrders.filter { it.id == selectedSoFilter }
                                    } else {
                                        salesOrders
                                    }
                                }

                                filteredOrders.forEachIndexed { idx, order ->
                                    val customer = order.description.ifBlank { order.item.ifBlank { "Customer for ${order.id}" } }
                                    val assignedWorkers = employees.filter { it.task == order.id }
                                    val categoryText = if (assignedWorkers.isNotEmpty()) {
                                        assignedWorkers.map { it.category }.distinct().joinToString(", ")
                                    } else {
                                        "${order.department} Specialist"
                                    }
                                    val workerCount = if (assignedWorkers.isNotEmpty()) "${assignedWorkers.size} workers" else "1 assigned"
                                    val plannedHr = getPlannedHrForOrder(order)
                                    val actualHr = getActualHrForOrder(order, employees)
                                    val cost = getActualCostForOrder(order, employees)

                                    Row(
                                        modifier = Modifier
                                            .background(if (idx % 2 == 1) Color(0xFFF8F9FA) else Color.White)
                                            .padding(horizontal = 16.dp, vertical = 11.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        TableCell(order.id, 110.dp, color = Color(0xFF2E1065), fontWeight = FontWeight.Bold, isMonospace = true)
                                        TableCell(customer, 160.dp, color = Color(0xFF2E1065))
                                        TableCell(categoryText, 140.dp, color = Color(0xFF6D28D9), fontWeight = FontWeight.SemiBold)
                                        TableCell(workerCount, 100.dp, color = Color(0xFF2E1065))
                                        TableCell("${String.format(Locale.US, "%.1f", plannedHr)} hrs", 110.dp, color = Color(0xFF2E1065))
                                        TableCell("${String.format(Locale.US, "%.1f", actualHr)} hrs", 120.dp, color = Color(0xFF2E1065))
                                        TableCell(formatCostValue(cost), 110.dp, color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                                    }

                                    if (idx < filteredOrders.size - 1) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(1.dp)
                                                .background(Color(0xFFF3E8FF))
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LightDropdownSelection(
    selectedValue: String,
    label: String,
    options: List<String>,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = label.uppercase(),
            color = Color(0xFF6D28D9),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true },
                shape = RoundedCornerShape(8.dp),
                color = Color.White,
                border = BorderStroke(1.dp, Color(0xFFD8B4FE))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedValue,
                        color = Color(0xFF2E1065),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Expand",
                        tint = Color(0xFF5B3A75),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.64f))
                    .border(1.dp, Color(0xFFD8B4FE), RoundedCornerShape(8.dp))
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                option,
                                color = Color(0xFF2E1065),
                                fontSize = 13.sp,
                                fontWeight = if (option == selectedValue) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        onClick = {
                            onSelect(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun LightDateInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = label.uppercase(),
            color = Color(0xFF6D28D9),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(
                color = Color(0xFF2E1065),
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            ),
            cursorBrush = SolidColor(Color(0xFF6D28D9)),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.64f), RoundedCornerShape(8.dp))
                        .border(1.dp, Color(0xFFD8B4FE), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            if (value.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    color = Color(0xFF6B4A7D),
                                    fontSize = 13.sp
                                )
                            }
                            innerTextField()
                        }
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Date Picker",
                            tint = Color(0xFF6B4A7D),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        )
    }
}

private data class ReportRowData(
    val soId: String,
    val customer: String,
    val category: String,
    val personnel: String,
    val plannedHrs: String,
    val totalManhours: String,
    val labourCost: String
)

private fun getCustomerNameForOrder(order: SalesOrder): String {
    return order.description.ifBlank { "Customer for ${order.id}" }
}

private fun getCustomerNameForOrder(id: String): String {
    return "Customer for $id"
}

private fun getPlannedCostForOrder(order: SalesOrder): Double {
    return order.plannedBudget.coerceAtLeast(0.0)
}

private fun getPlannedHrForOrder(order: SalesOrder): Double {
    return order.plannedManhours.coerceAtLeast(0.0)
}

private fun getActualHrForOrder(order: SalesOrder, employees: List<EmployeeActivity> = emptyList()): Double {
    val empHoursOnOrder = employees
        .filter { it.task == order.id }
        .sumOf { it.hoursClocked.coerceAtLeast(0.0) }
    if (empHoursOnOrder > 0) return empHoursOnOrder
    return order.timerSeconds.coerceAtLeast(0L) / 3600.0
}

private fun getActualCostForOrder(order: SalesOrder, employees: List<EmployeeActivity> = emptyList()): Double {
    val assignedEmployees = employees.filter { it.task == order.id }
    val empCost = assignedEmployees.sumOf {
        it.hoursClocked.coerceAtLeast(0.0) * it.hourlyRate.coerceAtLeast(0.0)
    }
    if (empCost > 0) return empCost
    val averageRate = assignedEmployees
        .map { it.hourlyRate.coerceAtLeast(0.0) }
        .takeIf { it.isNotEmpty() }
        ?.average()
        ?: return 0.0
    return getActualHrForOrder(order, employees) * averageRate
}

private fun getEfficiencyForOrder(order: SalesOrder, employees: List<EmployeeActivity> = emptyList()): Double {
    val plannedHr = getPlannedHrForOrder(order)
    val actualHr = getActualHrForOrder(order, employees)
    return when {
        order.status == "Not Started" -> 0.0
        actualHr > 0 -> {
            val progressRatio = if (order.targetQty > 0) order.completedQty.toDouble() / order.targetQty else 0.0
            (progressRatio / (actualHr / plannedHr)) * 100.0
        }
        else -> 0.0
    }
}

private fun formatCostValue(value: Double): String {
    return NumberFormat.getCurrencyInstance(Locale.forLanguageTag("en-IN")).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }.format(value)
}

@Composable
fun TableHeaderCell(text: String, width: androidx.compose.ui.unit.Dp) {
    Box(
        modifier = Modifier
            .width(width)
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            color = Color(0xFF5B3A75),
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp
        )
    }
}

@Composable
fun TableCell(
    text: String,
    width: androidx.compose.ui.unit.Dp,
    color: Color = Color(0xFF2E1065),
    fontWeight: FontWeight = FontWeight.Normal,
    isMonospace: Boolean = false
) {
    Box(
        modifier = Modifier
            .width(width)
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            color = color,
            fontWeight = fontWeight,
            fontSize = 11.sp,
            fontFamily = if (isMonospace) FontFamily.Monospace else FontFamily.Default
        )
    }
}

@Composable
fun OverallSalesOrderSummary(
    salesOrders: List<SalesOrder>,
    employees: List<EmployeeActivity> = emptyList(),
    onOrderClick: ((SalesOrder) -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.66f)),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD8B4FE))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.List,
                        contentDescription = "Summary icon",
                        tint = Color(0xFF6D28D9),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "OVERALL SALES ORDER SUMMARY",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6D28D9),
                        letterSpacing = 1.sp
                    )
                }
                
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFFEDE9FE))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        "FACTORY METRICS",
                        color = Color(0xFF2E1065),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Horizontally Scrollable Table
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
            ) {
                Column {
                    // Header Row
                    Row(
                        modifier = Modifier
                            .background(Color(0xFFF3E8FF), RoundedCornerShape(8.dp))
                            .padding(vertical = 8.dp, horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TableHeaderCell("SO ID", width = 80.dp)
                        TableHeaderCell("Customer Name", width = 140.dp)
                        TableHeaderCell("Planned Cost", width = 100.dp)
                        TableHeaderCell("Actual Cost", width = 100.dp)
                        TableHeaderCell("Planned Hr", width = 90.dp)
                        TableHeaderCell("Actual Hr", width = 90.dp)
                        TableHeaderCell("Status", width = 100.dp)
                        TableHeaderCell("Efficiency", width = 90.dp)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Rows
                    salesOrders.forEachIndexed { index, order ->
                        val customer = order.description.ifBlank { order.item.ifBlank { "Customer for ${order.id}" } }
                        val plannedCost = getPlannedCostForOrder(order)
                        val actualCost = getActualCostForOrder(order, employees)
                        val plannedHr = getPlannedHrForOrder(order)
                        val actualHr = getActualHrForOrder(order, employees)
                        val efficiency = getEfficiencyForOrder(order, employees)

                        val bgRowColor = if (index % 2 == 0) Color(0xFFF7F2FF) else Color.White

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(bgRowColor, RoundedCornerShape(4.dp))
                                .clickable(enabled = onOrderClick != null) { onOrderClick?.invoke(order) }
                                .padding(vertical = 8.dp, horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TableCell(order.id, width = 80.dp, isMonospace = true, fontWeight = FontWeight.Bold)
                            TableCell(customer, width = 140.dp)
                            TableCell(formatCostValue(plannedCost), width = 100.dp)
                            TableCell(formatCostValue(actualCost), width = 100.dp, color = if (actualCost > plannedCost && order.status != "Not Started") Color(0xFFF59E0B) else Color(0xFF2E1065))
                            TableCell(String.format("%.1f hrs", plannedHr), width = 90.dp)
                            TableCell(String.format("%.1f hrs", actualHr), width = 90.dp)

                            // Status badge
                            Box(modifier = Modifier.width(100.dp)) {
                                val statusColor = when (order.status) {
                                    "Completed" -> Color(0xFF2E7D32)
                                    "In Progress" -> Color(0xFF6D28D9)
                                    "Not Started" -> Color(0xFF6B4A7D)
                                    else -> Color(0xFF2E1065)
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(statusColor.copy(alpha = 0.12f))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        order.status,
                                        color = statusColor,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            // Efficiency
                            Box(modifier = Modifier.width(90.dp)) {
                                val effColor = when {
                                    efficiency >= 100.0 -> Color(0xFF2E7D32)
                                    efficiency >= 85.0 -> Color(0xFF6D28D9)
                                    efficiency > 0.0 -> Color(0xFFF59E0B)
                                    else -> Color(0xFF6B4A7D)
                                }
                                Text(
                                    text = if (efficiency > 0.0) String.format("%.1f%%", efficiency) else "—",
                                    color = effColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// Reusable Overall Summary Card styled like Sales Order Running Cards
@Composable
fun OverallSummaryCard(
    order: SalesOrder,
    employees: List<EmployeeActivity>,
    onEdit: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null,
    isDark: Boolean = false,
    onClick: () -> Unit
) {
    val customer = order.description.ifBlank { order.item.ifBlank { "Customer for ${order.id}" } }
    val actualHours = getActualHrForOrder(order, employees)
    val actualCost = getActualCostForOrder(order, employees)

    val statusColor = when (order.status) {
        "Completed" -> if (isDark) Color(0xFF00E676) else Color(0xFF2E7D32)
        "In Progress" -> if (isDark) Color(0xFFA855F7) else Color(0xFF6D28D9)
        "Not Started" -> if (isDark) Color(0xFFFEF3C7) else Color(0xFF6B4A7D)
        else -> if (isDark) Color.White else Color(0xFF2E1065)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDark) {
                Color(0xFF2E1065)
            } else {
                Color.White
            }
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (isDark) Color(0xFF6D28D9) else Color(0xFFD8B4FE))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Row: SO ID & Status Badge on right
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    order.id,
                    fontWeight = FontWeight.Bold,
                    color = if (isDark) Color.White else Color(0xFF2E1065),
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(statusColor.copy(alpha = if (isDark) 0.2f else 0.12f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        order.status.uppercase(),
                        color = statusColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Customer Name
            Text(
                text = customer,
                fontWeight = FontWeight.Bold,
                color = if (isDark) Color(0xFFA855F7) else Color(0xFF6D28D9),
                fontSize = 14.sp
            )

            // Work Description
            if (order.description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = order.description,
                    color = if (isDark) Color(0xFFFEF3C7) else Color(0xFF5B3A75),
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Budget and Manhours Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("PLANNED BUDGET", fontSize = 9.sp, color = if (isDark) Color(0xFFFEF3C7) else Color(0xFF6B4A7D), fontWeight = FontWeight.Bold)
                    Text(
                        text = formatCostValue(order.plannedBudget),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isDark) Color.White else Color(0xFF2E1065)
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("PLANNED MANHOURS", fontSize = 9.sp, color = if (isDark) Color(0xFFFEF3C7) else Color(0xFF6B4A7D), fontWeight = FontWeight.Bold)
                    Text(
                        text = "${String.format("%.1f", order.plannedManhours)} hrs",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isDark) Color.White else Color(0xFF2E1065)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("ACTUAL COST", fontSize = 9.sp, color = if (isDark) Color(0xFFFEF3C7) else Color(0xFF6B4A7D), fontWeight = FontWeight.Bold)
                    Text(
                        text = formatCostValue(actualCost),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isDark) Color(0xFF00E676) else Color(0xFF2E7D32)
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("ACTUAL HOURS", fontSize = 9.sp, color = if (isDark) Color(0xFFFEF3C7) else Color(0xFF6B4A7D), fontWeight = FontWeight.Bold)
                    Text(
                        text = "${String.format(Locale.US, "%.2f", actualHours)} hrs",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isDark) Color(0xFFA855F7) else Color(0xFF6D28D9)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(if (isDark) Color(0xFF6D28D9) else Color(0xFFF3E8FF)))
            Spacer(modifier = Modifier.height(8.dp))

            // Timeline Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Timeline: ${order.startDate.ifEmpty { "—" }} to ${order.endDate.ifEmpty { "—" }}",
                    fontSize = 11.sp,
                    color = if (isDark) Color(0xFFFEF3C7) else Color(0xFF6B4A7D),
                    modifier = Modifier.weight(1f)
                )

                if (onEdit != null || onDelete != null) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (onEdit != null) {
                            IconButton(
                                onClick = onEdit,
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = "Edit Sales Order",
                                    tint = Color(0xFF6D28D9),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                        if (onDelete != null) {
                            IconButton(
                                onClick = onDelete,
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Delete Sales Order",
                                    tint = Color(0xFFBA1A1A),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// Custom drawn Canvas comparison chart specifically for Sales Order Details
@Composable
fun SalesOrderCompareChart(
    plannedCost: Double,
    actualCost: Double,
    plannedHr: Double,
    actualHr: Double
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.66f)),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD8B4FE))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "RESOURCE COMPARISON PLAN VS ACTUAL",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6D28D9),
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val canvasWidth = size.width
                    val canvasHeight = size.height

                    // Draw baseline grid lines
                    val gridLines = 4
                    for (i in 0..gridLines) {
                        val y = (canvasHeight / gridLines) * i
                        drawLine(
                            color = Color(0xFFD8B4FE),
                            start = Offset(0f, y),
                            end = Offset(canvasWidth, y),
                            strokeWidth = 1f
                        )
                    }

                    // Layout calculations
                    val bottomPadding = 30.dp.toPx()
                    val topPadding = 20.dp.toPx()
                    val graphHeight = canvasHeight - bottomPadding - topPadding

                    val colWidth = canvasWidth / 2f
                    val barWidth = 40.dp.toPx()
                    val barGap = 8.dp.toPx()

                    // Category 1: Cost Comparison
                    val maxCost = maxOf(plannedCost, actualCost, 1.0)
                    val plannedCostHeight = (plannedCost / maxCost * graphHeight).toFloat()
                    val actualCostHeight = (actualCost / maxCost * graphHeight).toFloat()

                    // Category 2: Hours Comparison
                    val maxHr = maxOf(plannedHr, actualHr, 1.0)
                    val plannedHrHeight = (plannedHr / maxHr * graphHeight).toFloat()
                    val actualHrHeight = (actualHr / maxHr * graphHeight).toFloat()

                    val startY = canvasHeight - bottomPadding

                    // --- DRAW COST BARS ---
                    val costCenterX = colWidth / 2f
                    val plannedCostX = costCenterX - barWidth - barGap / 2f
                    val actualCostX = costCenterX + barGap / 2f

                    // Draw Planned Cost Bar
                    drawRoundRect(
                        color = Color(0xFF6D28D9),
                        topLeft = Offset(plannedCostX, startY - plannedCostHeight),
                        size = Size(barWidth, plannedCostHeight),
                        cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                    )

                    // Draw Actual Cost Bar
                    drawRoundRect(
                        color = if (actualCost > plannedCost) Color(0xFFF59E0B) else Color(0xFFEDE9FE),
                        topLeft = Offset(actualCostX, startY - actualCostHeight),
                        size = Size(barWidth, actualCostHeight),
                        cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                    )

                    // --- DRAW HOUR BARS ---
                    val hrCenterX = colWidth * 1.5f
                    val plannedHrX = hrCenterX - barWidth - barGap / 2f
                    val actualHrX = hrCenterX + barGap / 2f

                    // Draw Planned Hours Bar
                    drawRoundRect(
                        color = Color(0xFFA855F7),
                        topLeft = Offset(plannedHrX, startY - plannedHrHeight),
                        size = Size(barWidth, plannedHrHeight),
                        cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                    )

                    // Draw Actual Hours Bar
                    drawRoundRect(
                        color = Color(0xFFEDE9FE),
                        topLeft = Offset(actualHrX, startY - actualHrHeight),
                        size = Size(barWidth, actualHrHeight),
                        cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                    )

                    // Draw text labels and values
                    drawIntoCanvas { canvas ->
                        val paint = android.graphics.Paint().apply {
                            color = android.graphics.Color.parseColor("#0F172A")
                            textSize = 9.dp.toPx()
                            typeface = android.graphics.Typeface.DEFAULT_BOLD
                            textAlign = android.graphics.Paint.Align.CENTER
                        }

                        val labelPaint = android.graphics.Paint().apply {
                            color = android.graphics.Color.parseColor("#475569")
                            textSize = 10.dp.toPx()
                            typeface = android.graphics.Typeface.DEFAULT_BOLD
                            textAlign = android.graphics.Paint.Align.CENTER
                        }

                        // Cost values
                        canvas.nativeCanvas.drawText(
                            formatCostValue(plannedCost),
                            plannedCostX + barWidth / 2f,
                            startY - plannedCostHeight - 4.dp.toPx(),
                            paint
                        )
                        canvas.nativeCanvas.drawText(
                            formatCostValue(actualCost),
                            actualCostX + barWidth / 2f,
                            startY - actualCostHeight - 4.dp.toPx(),
                            paint
                        )

                        // Hours values
                        canvas.nativeCanvas.drawText(
                            "${String.format("%.1f", plannedHr)}h",
                            plannedHrX + barWidth / 2f,
                            startY - plannedHrHeight - 4.dp.toPx(),
                            paint
                        )
                        canvas.nativeCanvas.drawText(
                            "${String.format("%.1f", actualHr)}h",
                            actualHrX + barWidth / 2f,
                            startY - actualHrHeight - 4.dp.toPx(),
                            paint
                        )

                        // Bottom Labels
                        canvas.nativeCanvas.drawText(
                            "Production Cost (₹)",
                            costCenterX,
                            startY + 18.dp.toPx(),
                            labelPaint
                        )
                        canvas.nativeCanvas.drawText(
                            "Labour Time (Hours)",
                            hrCenterX,
                            startY + 18.dp.toPx(),
                            labelPaint
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Legend indicators
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(10.dp).background(Color(0xFF6D28D9), RoundedCornerShape(2.dp)))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Planned Cost", fontSize = 10.sp, color = Color(0xFF5B3A75))

                Spacer(modifier = Modifier.width(16.dp))

                Box(modifier = Modifier.size(10.dp).background(Color(0xFFEDE9FE), RoundedCornerShape(2.dp)))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Actual Cost", fontSize = 10.sp, color = Color(0xFF5B3A75))

                Spacer(modifier = Modifier.width(16.dp))

                Box(modifier = Modifier.size(10.dp).background(Color(0xFFA855F7), RoundedCornerShape(2.dp)))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Planned Hours", fontSize = 10.sp, color = Color(0xFF5B3A75))

                Spacer(modifier = Modifier.width(16.dp))

                Box(modifier = Modifier.size(10.dp).background(Color(0xFFEDE9FE), RoundedCornerShape(2.dp)))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Actual Hours", fontSize = 10.sp, color = Color(0xFF5B3A75))
            }
        }
    }
}

// Sales Order Detailed Screen with Custom KPIs, Canvas graph comparisons, and Assigned Workers
@Composable
fun SalesOrderDetailScreen() {
    // Legacy placeholder, actual screen is SalesOrderDetailsScreen with parameters
}

@Composable
fun SalesOrderDetailsScreen(
    order: SalesOrder,
    employees: List<EmployeeActivity>,
    onBack: () -> Unit
) {
    val customer = order.description.ifBlank { order.item.ifBlank { "Customer for ${order.id}" } }
    val plannedCost = getPlannedCostForOrder(order)
    val actualCost = getActualCostForOrder(order, employees)
    val plannedHr = getPlannedHrForOrder(order)
    val actualHr = getActualHrForOrder(order, employees)
    val efficiency = getEfficiencyForOrder(order, employees)
    val completionFraction = if (order.targetQty > 0) order.completedQty.toFloat() / order.targetQty.toFloat() else 0f
    val completionPercent = (completionFraction * 100).roundToInt()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // 1. Back button header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0xFFF3E8FF))
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF6D28D9)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "SALES ORDER RUN DETAILS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6D28D9),
                    letterSpacing = 1.sp
                )
                Text(
                    text = "${order.id} • $customer",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF2E1065)
                )
            }
        }

        // 2. High fidelity specific Sales Order Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.66f)),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD8B4FE))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "PRODUCTION SPECIFICATIONS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6D28D9),
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Manufactured Item: ${order.item}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E1065)
                )
                Text(
                    text = "Department Assignment: ${order.department}",
                    fontSize = 12.sp,
                    color = Color(0xFF5B3A75)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Completed: ${order.completedQty} / ${order.targetQty} units",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF2E1065)
                    )
                    Text(
                        text = "$completionPercent%",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF6D28D9)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { completionFraction },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(CircleShape),
                    color = Color(0xFF6D28D9),
                    trackColor = Color(0xFFF3E8FF)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 3. KPI Grid específica de esta orden
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            KpiCard(
                title = "Total Cost (Act/Plan)",
                value = formatCostValue(actualCost),
                sub = "Planned Budget: ${formatCostValue(plannedCost)}",
                icon = Icons.Filled.ShoppingCart,
                tint = if (actualCost > plannedCost && order.status != "Not Started") Color(0xFFF59E0B) else Color(0xFF2E7D32),
                modifier = Modifier.weight(1f)
            )

            KpiCard(
                title = "Production Time",
                value = "${String.format("%.1f", actualHr)} hrs",
                sub = "Planned Target: ${String.format("%.1f", plannedHr)} hrs",
                icon = Icons.Filled.DateRange,
                tint = Color(0xFFA855F7),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            KpiCard(
                title = "Order Efficiency",
                value = if (efficiency > 0.0) String.format("%.1f%%", efficiency) else "—",
                sub = "Target Output Yield Index",
                icon = Icons.Filled.ThumbUp,
                tint = when {
                    efficiency >= 100.0 -> Color(0xFF2E7D32)
                    efficiency >= 85.0 -> Color(0xFF6D28D9)
                    efficiency > 0.0 -> Color(0xFFF59E0B)
                    else -> Color(0xFF6B4A7D)
                },
                modifier = Modifier.weight(1f)
            )

            KpiCard(
                title = "Production Status",
                value = order.status,
                sub = "Active Workflow State",
                icon = Icons.Filled.CheckCircle,
                tint = when (order.status) {
                    "Completed" -> Color(0xFF2E7D32)
                    "In Progress" -> Color(0xFF6D28D9)
                    else -> Color(0xFF6B4A7D)
                },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 4. Custom drawn Canvas resource comparison chart
        SalesOrderCompareChart(
            plannedCost = plannedCost,
            actualCost = actualCost,
            plannedHr = plannedHr,
            actualHr = actualHr
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 5. Worker Table Details Card
        val filteredWorkers = employees

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.66f)),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD8B4FE))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Workers Icon",
                            tint = Color(0xFF6D28D9),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "ALL EMPLOYEES (${filteredWorkers.size})",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6D28D9),
                            letterSpacing = 1.sp
                        )
                    }
                    
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFFEDE9FE))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            "ALL DEPARTMENTS",
                            color = Color(0xFF2E1065),
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (filteredWorkers.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No employees have been added yet.", color = Color(0xFF6B4A7D), fontSize = 12.sp)
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                    ) {
                        Column {
                            // Table Headers
                            Row(
                                modifier = Modifier
                                    .background(Color(0xFFF3E8FF), RoundedCornerShape(8.dp))
                                    .padding(vertical = 8.dp, horizontal = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TableHeaderCell("EMP ID", width = 80.dp)
                                TableHeaderCell("Worker Name", width = 140.dp)
                                TableHeaderCell("Actual Cost", width = 100.dp)
                                TableHeaderCell("Actual Hrs", width = 90.dp)
                                TableHeaderCell("Status", width = 100.dp)
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            // Table Rows
                            filteredWorkers.forEachIndexed { idx, emp ->
                                val empId = getEmpIdByName(emp.name)
                                val empHrs = emp.hoursClocked
                                val empCost = empHrs * emp.hourlyRate
                                val bgRow = if (idx % 2 == 0) Color(0xFFF7F2FF) else Color.White

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(bgRow, RoundedCornerShape(4.dp))
                                        .padding(vertical = 8.dp, horizontal = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TableCell(empId, width = 80.dp, isMonospace = true, fontWeight = FontWeight.Bold)
                                    TableCell(emp.name, width = 140.dp, fontWeight = FontWeight.SemiBold)
                                    TableCell(formatCostValue(empCost), width = 100.dp)
                                    TableCell(String.format("%.1f hrs", empHrs), width = 90.dp)

                                    // Status Badge
                                    Box(modifier = Modifier.width(100.dp)) {
                                        val statusColor = when (emp.status) {
                                            "Active" -> Color(0xFF2E7D32)
                                            "Break" -> Color(0xFFF59E0B)
                                            else -> Color(0xFF6B4A7D)
                                        }
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(statusColor.copy(alpha = 0.12f))
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = emp.status,
                                                color = statusColor,
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun AdaptiveLanguagePair(
    stacked: Boolean,
    modifier: Modifier = Modifier,
    first: @Composable (Modifier) -> Unit,
    second: @Composable (Modifier) -> Unit
) {
    if (stacked) {
        Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
            first(Modifier.fillMaxWidth())
            second(Modifier.fillMaxWidth())
        }
    } else {
        Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            first(Modifier.weight(1.1f))
            second(Modifier.weight(1f))
        }
    }
}

@Composable
private fun AdaptiveActionButtons(
    stacked: Boolean,
    showUnassign: Boolean,
    modifier: Modifier = Modifier,
    unassign: @Composable (Modifier) -> Unit,
    shift: @Composable (Modifier) -> Unit
) {
    if (stacked) {
        Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            if (showUnassign) unassign(Modifier.fillMaxWidth())
            shift(Modifier.fillMaxWidth())
        }
    } else {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showUnassign) unassign(Modifier.weight(1f))
            shift(Modifier.weight(1f))
        }
    }
}

@Composable
private fun OverlappingAvatars(orderEmployees: List<EmployeeActivity>) {
    Row(
        horizontalArrangement = Arrangement.spacedBy((-10).dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        orderEmployees.take(3).forEachIndexed { index, emp ->
            val avatarColor = when (index % 4) {
                0 -> Color(0xFFA855F7) // Cyan
                1 -> Color(0xFFFF4081) // Pink
                2 -> Color(0xFF00E676) // Green
                else -> Color(0xFFFFD600) // Yellow
            }
            val textColor = when (index % 4) {
                0 -> Color.White
                1 -> Color.White
                2 -> Color(0xFF1B5E20)
                else -> Color(0xFF4E342E)
            }
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(avatarColor)
                    .border(1.5.dp, Color(0xFF11131E), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = emp.name.take(1).uppercase(),
                    color = textColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        if (orderEmployees.size > 3) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF37474F))
                    .border(1.5.dp, Color(0xFF11131E), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+${orderEmployees.size - 3}",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// Generates an ID for legacy records that do not already have one.
fun getEmpIdByName(name: String): String {
    val hashCode = Math.abs(name.hashCode()) % 10000
    return "EMP-$hashCode"
}

fun generateNextSalesOrderId(orders: List<SalesOrder>): String {
    val highestNumber = orders.mapNotNull { order ->
        Regex("""^(?:SO-)?(\d+)$""", RegexOption.IGNORE_CASE)
            .matchEntire(order.id.trim())?.groupValues?.getOrNull(1)?.toIntOrNull()
    }.maxOrNull() ?: 0
    var candidate = highestNumber + 1
    var id = "SO-${candidate.toString().padStart(2, '0')}"
    val existing = orders.map { it.id.uppercase() }.toSet()
    while (id in existing) {
        candidate++
        id = "SO-${candidate.toString().padStart(2, '0')}"
    }
    return id
}

fun generateNextEmployeeId(employees: List<EmployeeActivity>): String {
    val highestNumber = employees
        .mapNotNull { employee ->
            Regex("""^EMP-(\d+)$""", RegexOption.IGNORE_CASE)
                .matchEntire(employee.empId.trim())
                ?.groupValues
                ?.getOrNull(1)
                ?.toIntOrNull()
        }
        .maxOrNull()
        ?: 0
    return "EMP-${(highestNumber + 1).toString().padStart(2, '0')}"
}

fun generateNextDepartmentId(departments: List<Department>): String {
    val highestNumber = departments.mapNotNull { department ->
        Regex("""^DEP-(\d+)$""", RegexOption.IGNORE_CASE)
            .matchEntire(department.code.trim())?.groupValues?.getOrNull(1)?.toIntOrNull()
    }.maxOrNull() ?: 0
    return "DEP-${(highestNumber + 1).toString().padStart(2, '0')}"
}

fun generateNextCategoryId(categories: List<LabourCategory>): String {
    val highestNumber = categories.mapNotNull { category ->
        Regex("""^CAT-(\d+)$""", RegexOption.IGNORE_CASE)
            .matchEntire(category.code.trim())?.groupValues?.getOrNull(1)?.toIntOrNull()
    }.maxOrNull() ?: 0
    return "CAT-${(highestNumber + 1).toString().padStart(2, '0')}"
}

@Composable
fun DarkInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isRequired: Boolean = false,
    enabled: Boolean = true
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = label.uppercase(),
                color = Color(0xFF5B3A75),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )
            if (isRequired) {
                Text(
                    text = " *",
                    color = Color(0xFFEF4444),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            singleLine = true,
            textStyle = TextStyle(
                color = Color(0xFF2E1065),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            ),
            cursorBrush = SolidColor(Color(0xFF3B82F6)),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.64f), RoundedCornerShape(8.dp))
                        .border(1.dp, Color(0xFFD8B4FE), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 12.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = "Enter $label",
                            color = Color(0xFF94A3B8),
                            fontSize = 14.sp
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun DarkDropdownSelection(
    selectedValue: String,
    label: String,
    isRequired: Boolean = false,
    options: List<String>,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = label.uppercase(),
                color = Color(0xFF5B3A75),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )
            if (isRequired) {
                Text(
                    text = " *",
                    color = Color(0xFFEF4444),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true },
                shape = RoundedCornerShape(8.dp),
                color = Color.White,
                border = BorderStroke(1.dp, Color(0xFFD8B4FE))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedValue.ifEmpty { "Select $label" },
                        color = if (selectedValue.isEmpty()) Color(0xFF94A3B8) else Color(0xFF2E1065),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Expand",
                        tint = Color(0xFF6B4A7D),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.64f))
                    .border(1.dp, Color(0xFFD8B4FE), RoundedCornerShape(8.dp))
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                option,
                                color = Color(0xFF2E1065),
                                fontSize = 13.sp,
                                fontWeight = if (option == selectedValue) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        onClick = {
                            onSelect(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun EmployeeFormModalDialog(
    title: String,
    initialEmpId: String,
    initialName: String,
    initialDept: String,
    initialCategory: String,
    initialSkill: String,
    initialStatus: String,
    initialAssignedSo: String,
    departmentsList: List<Department>,
    categoriesList: List<LabourCategory>,
    salesOrders: List<SalesOrder>,
    allEmployees: List<EmployeeActivity>,
    onDismiss: () -> Unit,
    onSave: (empId: String, name: String, dept: String, cat: String, skill: String, status: String, assignedSo: String) -> Unit
) {
    var empIdInput by remember(initialEmpId, title) {
        mutableStateOf(
            initialEmpId.ifEmpty {
                if (title.equals("New Employee", ignoreCase = true)) generateNextEmployeeId(allEmployees) else ""
            }
        )
    }
    var nameInput by remember(initialName) { mutableStateOf(initialName) }
    var deptInput by remember(initialDept, departmentsList) { mutableStateOf(initialDept.ifEmpty { departmentsList.firstOrNull()?.name.orEmpty() }) }
    var categoryInput by remember(initialCategory, initialDept, categoriesList) {
        mutableStateOf(
            initialCategory.ifEmpty {
                categoriesList.firstOrNull {
                    it.department.equals(deptInput, ignoreCase = true)
                }?.name.orEmpty()
            }
        )
    }
    var skillInput by remember(initialSkill) { mutableStateOf(initialSkill.ifEmpty { "Expert" }) }
    var statusInput by remember(initialStatus) { mutableStateOf(initialStatus.ifEmpty { "Active" }) }

    val formattedInitialSo = if (initialAssignedSo.isEmpty() || initialAssignedSo.equals("Assigned Duty", ignoreCase = true) || initialAssignedSo.equals("Unassigned", ignoreCase = true)) {
        "Unassigned"
    } else {
        val matchingSo = salesOrders.find { it.id == initialAssignedSo }
        if (matchingSo != null) {
            "${matchingSo.id} — ${matchingSo.description.ifEmpty { matchingSo.item }}"
        } else {
            initialAssignedSo
        }
    }

    var assignedSoInput by remember(formattedInitialSo) { mutableStateOf(formattedInitialSo) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .widthIn(max = 520.dp)
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFF7F2FF),
            border = BorderStroke(1.dp, Color(0xFFD8B4FE)),
            shadowElevation = 12.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        color = Color(0xFF2E1065),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color(0xFF6B4A7D)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                // Row 1: EMPLOYEE ID * & FULL NAME *
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        DarkInputField(
                            value = empIdInput,
                            onValueChange = { empIdInput = it },
                            label = "EMPLOYEE ID",
                            isRequired = true
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        DarkInputField(
                            value = nameInput,
                            onValueChange = { nameInput = it },
                            label = "FULL NAME",
                            isRequired = true
                        )
                    }
                }

                // Row 2: DEPARTMENT * & LABOUR CATEGORY *
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        DarkDropdownSelection(
                            selectedValue = deptInput,
                            label = "DEPARTMENT",
                            isRequired = true,
                            options = departmentsList.map { it.name }
                        ) {
                            deptInput = it
                            if (categoriesList.none { category ->
                                    category.name == categoryInput &&
                                            category.department.equals(it, ignoreCase = true)
                                }) {
                                categoryInput = ""
                            }
                        }
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        DarkDropdownSelection(
                            selectedValue = categoryInput,
                            label = "LABOUR CATEGORY",
                            isRequired = true,
                            options = categoriesList
                                .filter { it.department.isBlank() || it.department.equals(deptInput, ignoreCase = true) }
                                .map { it.name }
                        ) {
                            categoryInput = it
                        }
                    }
                }

                // Row 3: SKILL LEVEL & STATUS
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        DarkDropdownSelection(
                            selectedValue = skillInput,
                            label = "SKILL LEVEL",
                            options = listOf("Expert", "Skilled", "Semi Skilled", "Non Skilled", "Intermediate", "Novice", "Apprentice", "Supervisor")
                        ) {
                            skillInput = it
                        }
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        DarkDropdownSelection(
                            selectedValue = statusInput,
                            label = "STATUS",
                            options = listOf("Active", "Break", "Logged Out", "Inactive")
                        ) {
                            statusInput = it
                        }
                    }
                }

                // Row 4: ASSIGN TO SALES ORDER
                val soOptions = listOf("Unassigned") + salesOrders.map { "${it.id} — ${it.description.ifEmpty { it.item }}" }
                DarkDropdownSelection(
                    selectedValue = assignedSoInput,
                    label = "ASSIGN TO SALES ORDER",
                    options = soOptions
                ) {
                    assignedSoInput = it
                }

                // Selected Sales Order Sub-Card
                val currentSelectedSoId = assignedSoInput.split(" — ").firstOrNull() ?: ""
                val selectedSoObj = salesOrders.find { it.id == currentSelectedSoId }

                if (selectedSoObj != null) {
                    val assignedCount = allEmployees.count { it.task == selectedSoObj.id }
                    val custName = selectedSoObj.description.ifEmpty { selectedSoObj.item }

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, Color(0xFFD8B4FE))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF6D28D9)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Assignment,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = custName,
                                    color = Color(0xFF2E1065),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "${selectedSoObj.plannedManhours.toInt()} hrs planned · $assignedCount employees assigned",
                                    color = Color(0xFF6B4A7D),
                                    fontSize = 12.sp
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            val statusDisplay = if (selectedSoObj.status == "Not Started") "Not Started" else if (selectedSoObj.status == "Running") "In Progress" else selectedSoObj.status
                            val (badgeBg, badgeText, badgeBorder) = when (statusDisplay) {
                                "In Progress", "Running" -> Triple(Color(0xFF451A03), Color(0xFFFB923C), Color(0xFF7C2D12))
                                "Completed" -> Triple(Color(0xFF064E3B), Color(0xFF34D399), Color(0xFF065F46))
                                else -> Triple(Color(0xFFF3E8FF), Color(0xFF6B4A7D), Color(0xFFD8B4FE))
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(badgeBg)
                                    .border(1.dp, badgeBorder, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = statusDisplay,
                                    color = badgeText,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                } else {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, Color(0xFFD8B4FE))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFD8B4FE)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.WorkOutline,
                                    contentDescription = null,
                                    tint = Color(0xFF6B4A7D),
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Unassigned Operator",
                                    color = Color(0xFF2E1065),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Operator is not assigned to any Sales Order",
                                    color = Color(0xFF6B4A7D),
                                    fontSize = 12.sp
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFFF3E8FF))
                                    .border(1.dp, Color(0xFFD8B4FE), RoundedCornerShape(12.dp))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "Available",
                                    color = Color(0xFF9CA3AF),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Bottom Buttons Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White.copy(alpha = 0.66f),
                            contentColor = Color(0xFF5B3A75)
                        ),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Color(0xFFD8B4FE)),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
                    ) {
                        Text("Cancel", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    val rawSoId = if (assignedSoInput == "Unassigned") "Unassigned" else assignedSoInput.split(" — ").first()

                    Button(
                        onClick = {
                            if (
                                empIdInput.isNotBlank() &&
                                nameInput.isNotBlank() &&
                                deptInput.isNotBlank() &&
                                categoryInput.isNotBlank()
                            ) {
                                onSave(
                                    empIdInput.trim().uppercase(),
                                    nameInput.trim(),
                                    deptInput,
                                    categoryInput,
                                    skillInput,
                                    statusInput,
                                    rawSoId
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6D28D9),
                            contentColor = Color.White
                        ),
                        enabled = empIdInput.isNotBlank() &&
                            nameInput.isNotBlank() &&
                            deptInput.isNotBlank() &&
                            categoryInput.isNotBlank(),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Save Employee", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

private data class ExportFormatInfo(
    val label: String,
    val extension: String,
    val badgeText: String,
    val color: Color,
    val icon: ImageVector,
    val description: String
)
