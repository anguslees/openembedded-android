SECTION = "openmoko/libs"
DEPENDS = "gtk+ matchbox-panel-2"
SRCREV = "4568"
PV = "0.3.0+svnr${SRCPV}"
PR = "r0"

inherit openmoko2

do_stage() {
        autotools_stage_all
}
