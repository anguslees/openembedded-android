DESCRIPTION = "Extensions to the standard Python date/time support"
HOMEPAGE = "http://labix.org/python-dateutil"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "PSF"
SRCNAME = "${PN}"
PR = "r1"

SRC_URI = "http://labix.org/download/python-dateutil/${SRCNAME}-${PV}.tar.gz"
S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit setuptools

PACKAGES =+ "${PN}-zoneinfo"
FILES_${PN}-zoneinfo = "${libdir}/${PYTHON_DIR}/site-packages/dateutil/zoneinfo"

RDEPENDS_${PN} = "\
  python-core \
  python-datetime \
"

SRC_URI[md5sum] = "2a5f25ab12fcefcf0b21348f2d47595a"
SRC_URI[sha256sum] = "74b615c6a55b4421187feba1633fc233e7c5ebdd7abe9b092447a32946823357"
