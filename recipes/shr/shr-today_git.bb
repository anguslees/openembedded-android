DESCRIPTION = "python-elementary and opimd based lock and today screen for the SHR distribution"
HOMEPAGE = "http://wiki.github.com/slyon/today"
SHR_RELEASE ?= "shr"
LICENSE ?= "GPL"
RDEPENDS = "python-edje python-dbus python-edbus python-ecore"
SECTION = "x11/applications"

SRCREV = "7b69649a9df0e85f0c0f7985fd1d93543c3b11e2"
PV = "0.0.1+gitr${SRCPV}"
PR = "r2"

inherit distutils

SRC_URI = "git://github.com/slyon/today.git;protocol=http"
S = "${WORKDIR}/git"

FILES_${PN} += "/etc/shr-today.conf"
FILES_${PN} += "/usr/share/shr-today"
FILES_${PN} += "/etc/X11/Xsession.d/89shr-today"
CONFFILES_${PN} += "/etc/shr-today.conf"

