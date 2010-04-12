SECTION = "openmoko/libs"
DEPENDS = "eds-dbus"
SRCREV = "3473"
PV = "0.1.0+svnr${SRCPV}"
PR = "r2"

inherit openmoko2 lib_package

do_configure_prepend() {
        touch gtk-doc.make
}

do_stage() {
        autotools_stage_all
}

