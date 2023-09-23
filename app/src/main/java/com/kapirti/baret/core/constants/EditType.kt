package com.kapirti.baret.core.constants

object EditType {
    const val PROFILE = "PROFILE"
    const val DISPLAY_NAME = "DISPLAY_NAME"
    const val NAME_SURNAME = "NAME_SURNAME"
    const val CONTACT_DESCRIPTION = "CONTACT_DESCRIPTION"
    const val PHOTO = "PHOTO"

    const val CAR_SELL = "CAR_SELL"
    const val LOCAL_SHIPPING_RENT = "LOCAL_SHIPPING_RENT"
    const val LOCAL_SHIPPING_SELL = "LOCAL_SHIPPING_SELL"
    const val WORK_MACHINE_RENT = "WORK_MACHINE_RENT"
    const val WORK_MACHINE_SELL = "WORK_MACHINE_SELL"

//    const val CITY = "City"
    const val FEEDBACK = "FEEDBACK"
}

/**
//    const val CAR_RENT = "CAR_RENT"


 * private const val AGRICULTURE_COLLECTION = AGRICULTURE
private const val MACHINE_COLLECTION = MACHINE
private const val SHIP_COLLECTION = SHIP
private const val TRUCK_COLLECTION = TRUCK
 *

const val AGRICULTURE =  "Agriculture"
const val TRUCK = "Truck"
const val SHIP = "Ship"
const val MACHINE = "Machine"
 *
 *
fun engineTextExt (type: String): Int {
return when (type) {
AGRICULTURE -> AppText.agriculture
TRUCK -> AppText.truck
SHIP -> AppText.ship
MACHINE -> AppText.machine

else -> AppText.machine
}
} *



fun engineIconExt (type: String): ImageVector {
return when (type) {
AGRICULTURE -> Icons.Default.Agriculture
TRUCK -> Icons.Default.LocalShipping
SHIP -> Icons.Default.DirectionsBoat
MACHINE -> Icons.Default.BikeScooter

else -> Icons.Default.BikeScooter
 * */