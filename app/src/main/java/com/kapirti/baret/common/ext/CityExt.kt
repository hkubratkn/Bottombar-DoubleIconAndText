package com.kapirti.baret.common.ext

import com.kapirti.baret.core.constants.ConsCity.ADANA
import com.kapirti.baret.core.constants.ConsCity.ADIYAMAN
import com.kapirti.baret.core.constants.ConsCity.AFYON
import com.kapirti.baret.core.constants.ConsCity.AGRI
import com.kapirti.baret.core.constants.ConsCity.AKSARAY
import com.kapirti.baret.core.constants.ConsCity.AMASYA
import com.kapirti.baret.core.constants.ConsCity.ANKARA
import com.kapirti.baret.core.constants.ConsCity.ANTALYA
import com.kapirti.baret.core.constants.ConsCity.ARDAHAN
import com.kapirti.baret.core.constants.ConsCity.ARTVIN
import com.kapirti.baret.core.constants.ConsCity.AYDIN
import com.kapirti.baret.core.constants.ConsCity.BALIKESIR
import com.kapirti.baret.core.constants.ConsCity.BARTIN
import com.kapirti.baret.core.constants.ConsCity.BILECIK
import com.kapirti.baret.core.constants.ConsCity.BINGOL
import com.kapirti.baret.core.constants.ConsCity.BITLIS
import com.kapirti.baret.core.constants.ConsCity.BOLU
import com.kapirti.baret.core.constants.ConsCity.BURDUR
import com.kapirti.baret.core.constants.ConsCity.BURSA
import com.kapirti.baret.core.constants.ConsCity.CANAKKALE
import com.kapirti.baret.core.constants.ConsCity.CANKIRI
import com.kapirti.baret.core.constants.ConsCity.CORUM
import com.kapirti.baret.core.constants.ConsCity.DENIZLI
import com.kapirti.baret.core.constants.ConsCity.DIYARBAKIR
import com.kapirti.baret.core.constants.ConsCity.DUZCE
import com.kapirti.baret.core.constants.ConsCity.EDIRNE
import com.kapirti.baret.core.constants.ConsCity.ELAZIG
import com.kapirti.baret.core.constants.ConsCity.ERZINCAN
import com.kapirti.baret.core.constants.ConsCity.ERZURUM
import com.kapirti.baret.core.constants.ConsCity.ESKISEHIR
import com.kapirti.baret.core.constants.ConsCity.GAZIANTEP
import com.kapirti.baret.core.constants.ConsCity.GIRESUN
import com.kapirti.baret.core.constants.ConsCity.GUMUSHANE
import com.kapirti.baret.core.constants.ConsCity.HAKKARI
import com.kapirti.baret.core.constants.ConsCity.HATAY
import com.kapirti.baret.core.constants.ConsCity.IGDIR
import com.kapirti.baret.core.constants.ConsCity.ISPARTA
import com.kapirti.baret.core.constants.ConsCity.ISTANBUL
import com.kapirti.baret.core.constants.ConsCity.IZMIR
import com.kapirti.baret.core.constants.ConsCity.KARABUK
import com.kapirti.baret.core.constants.ConsCity.KARS
import com.kapirti.baret.core.constants.ConsCity.KASTAMONU
import com.kapirti.baret.core.constants.ConsCity.KAYSERI
import com.kapirti.baret.core.constants.ConsCity.KILIS
import com.kapirti.baret.core.constants.ConsCity.KIRKLARELI
import com.kapirti.baret.core.constants.ConsCity.KIRSEHIR
import com.kapirti.baret.core.constants.ConsCity.KOCAELI
import com.kapirti.baret.core.constants.ConsCity.KONYA
import com.kapirti.baret.core.constants.ConsCity.KUTAHYA
import com.kapirti.baret.core.constants.ConsCity.MERSIN
import com.kapirti.baret.core.constants.ConsCity.OSMANIYE
import com.kapirti.baret.core.constants.ConsCity.SAKARYA
import com.kapirti.baret.core.constants.ConsCity.SAMSUN
import com.kapirti.baret.core.constants.ConsCity.SANLIURFA
import com.kapirti.baret.core.constants.ConsCity.SIIRT
import com.kapirti.baret.core.constants.ConsCity.SINOP
import com.kapirti.baret.core.constants.ConsCity.SIRNAK
import com.kapirti.baret.core.constants.ConsCity.SIVAS
import com.kapirti.baret.core.constants.ConsCity.TUNCELI
import com.kapirti.baret.core.constants.ConsCity.USAK
import com.kapirti.baret.core.constants.ConsCity.VAN
import com.kapirti.baret.core.constants.ConsCity.YALOVA
import com.kapirti.baret.core.constants.ConsCity.YOZGAT
import com.kapirti.baret.core.constants.ConsCity.ZONGULDAK
import com.kapirti.baret.core.constants.ConsCity.BATMAN
import com.kapirti.baret.core.constants.ConsCity.KAHRAMANMARAS
import com.kapirti.baret.core.constants.ConsCity.MARDIN
import com.kapirti.baret.core.constants.ConsCity.MUGLA
import com.kapirti.baret.core.constants.ConsCity.MUS
import com.kapirti.baret.core.constants.ConsCity.NEVSEHIR
import com.kapirti.baret.core.constants.ConsCity.MALATYA
import com.kapirti.baret.core.constants.ConsCity.MANISA
import com.kapirti.baret.core.constants.ConsCity.ORDU
import com.kapirti.baret.core.constants.ConsCity.NIGDE
import com.kapirti.baret.core.constants.ConsCity.RIZE
import com.kapirti.baret.core.constants.ConsCity.BAYBURT
import com.kapirti.baret.core.constants.ConsCity.KARAMAN
import com.kapirti.baret.core.constants.ConsCity.KIRIKKALE
import com.kapirti.baret.core.constants.ConsCity.TRABZON
import com.kapirti.baret.core.constants.ConsCity.TOKAT
import com.kapirti.baret.core.constants.ConsCity.TEKIRDAG

fun cityExt (city: Int): String {
    return when (city) {
        1 -> ADANA
        2 -> ADIYAMAN
        3 -> AFYON
        4 -> AGRI
        5 -> AMASYA
        6 -> ANKARA
        7 -> ANTALYA
        8 -> ARTVIN
        9 -> AYDIN
        10 -> BALIKESIR
        11 -> BILECIK
        12 -> BINGOL
        13 -> BITLIS
        14 -> BOLU
        15 -> BURDUR
        16 -> BURSA
        17 -> CANAKKALE
        18 -> CANKIRI
        19 -> CORUM
        20 -> DENIZLI
        21 -> DIYARBAKIR
        22 -> EDIRNE
        23 -> ELAZIG
        24 -> ERZINCAN
        25 -> ERZURUM
        26 -> ESKISEHIR
        27 -> GAZIANTEP
        28 -> GIRESUN
        29 -> GUMUSHANE
        30 -> HAKKARI
        31 -> HATAY
        32 -> ISPARTA
        33 -> MERSIN
        34 -> ISTANBUL
        35 -> IZMIR
        36 -> KARS
        37 -> KASTAMONU
        38 -> KAYSERI
        39 -> KIRKLARELI
        40 -> KIRSEHIR
        41 -> KOCAELI
        42 -> KONYA
        43 -> KUTAHYA
        44 -> MALATYA
        45 -> MANISA
        46 -> KAHRAMANMARAS
        47 -> MARDIN
        48 -> MUGLA
        49 -> MUS
        50 -> NEVSEHIR
        51 -> NIGDE
        52 -> ORDU
        53 -> RIZE
        54 -> SAKARYA
        55 -> SAMSUN
        56 -> SIIRT
        57 -> SINOP
        58 -> SIVAS
        59 -> TEKIRDAG
        60 -> TOKAT
        61 -> TRABZON
        62 -> TUNCELI
        63 -> SANLIURFA
        64 -> USAK
        65 -> VAN
        66 -> YOZGAT
        67 -> ZONGULDAK
        68 -> AKSARAY
        69 -> BAYBURT
        70 -> KARAMAN
        71 -> KIRIKKALE
        72 -> BATMAN
        73 -> SIRNAK
        74 -> BARTIN
        75 -> ARDAHAN
        76 -> IGDIR
        77 -> YALOVA
        78 -> KARABUK
        79 -> KILIS
        80 -> OSMANIYE
        81 -> DUZCE

        else -> "Kıbrıs"
    }
}