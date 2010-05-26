require clutter.inc

PV = "0.4.0+svnr${SRCPV}"
PR = "${INC_PR}.0"
SRCREV = "3240"

SRC_URI = "svn://svn.o-hand.com/repos/clutter/branches;module=clutter-0-4;proto=http \
	   file://enable_tests-0.4.patch "

S = "${WORKDIR}/clutter-0-4"

do_stage () {
        cp ${S}/clutter.pc ${S}/clutter-0.4.pc
        autotools_stage_all
}
