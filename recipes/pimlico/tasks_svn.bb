require tasks.inc

DEFAULT_PREFERENCE = "-1"

PV = "0.13+svnr${SRCPV}"
S = "${WORKDIR}/trunk"

SRC_URI = "svn://svn.o-hand.com/repos/${PN};module=trunk;proto=http \
        file://tasks-owl.diff"
