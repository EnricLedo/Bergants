package cat.copernic.bergants.model

import androidx.annotation.Keep

@Keep
data class ActuacioBusModel(val titolActuacio: String, val dataActuacio: String, val llocActuacio: String, val autocar: ArrayList<BusModel>)
