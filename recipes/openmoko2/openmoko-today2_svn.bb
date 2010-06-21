DESCRIPTION = "The Openmoko Application Launcher"
SECTION = "openmoko/pim"
DEPENDS = "libmokoui2 libmokojournal2 libjana startup-notification dbus-glib libice libsm"
RDEPENDS_${PN} = "libedata-cal openmoko-today2-folders"
SRCREV = "4168"
PV = "0.1.0+svnr${SRCPV}"
PR = "r3"

inherit openmoko2 gtk-icon-cache 
