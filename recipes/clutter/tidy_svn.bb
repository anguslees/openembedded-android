require tidy.inc

PV = "0.1.0+svnr${SRCPV}"
PR = "6"

SRC_URI = "svn://svn.o-hand.com/repos/tidy;module=trunk;proto=http \
           file://tidy-enable-tests.patch"

S = "${WORKDIR}/trunk"

