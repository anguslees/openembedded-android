SECTION = "base"
SRCDATE = "20060814"
PV = "1.2+cvs${SRCDATE}"

require prism54-module_1.2.bb

SRC_URI = "http://prism54.org/pub/linux/snapshot/tars/prism54-cvs-latest.tar.bz2"
S = "${WORKDIR}/prism54-cvs-latest"
