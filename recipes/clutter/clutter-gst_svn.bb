require clutter-gst.inc

PV = "0.8.0+svnr${SRCPV}"
SRCREV = "3188"

SRC_URI = "svn://svn.o-hand.com/repos/clutter/trunk;module=${PN};proto=http \
           file://autofoo.patch"

S = "${WORKDIR}/${PN}"
