DESCRIPTION = "A Pure Python Expect like Module for Python"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "PSF"
SRCNAME = "pexpect"
PR = "ml1"

SRC_URI = "${SOURCEFORGE_MIRROR}/${SRCNAME}/${SRCNAME}-${PV}.tar.gz"
S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit distutils

RDEPENDS_${PN} = "\
  python-core \
  python-io \
  python-terminal \
  python-resource \
  python-fcntl \
"


SRC_URI[md5sum] = "bf107cf54e67bc6dec5bea1f3e6a65c3"
SRC_URI[sha256sum] = "d315e7f3a8544fd85034d7e17fd7c5854e8f0828f5791f83cf313f8fa5740b75"
