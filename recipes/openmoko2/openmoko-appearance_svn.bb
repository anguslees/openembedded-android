DESCRIPTION = "The Openmoko Appearance Editor"
SECTION = "openmoko/pim"
DEPENDS = "libmokoui2 gconf gtk+"
RDEPENDS = "libedata-cal openmoko-today2-folders"
SRCREV = "3262"
PV = "0.1.0+svnr${SRCPV}"
PR = "r0"

inherit openmoko2 gtk-icon-cache 
