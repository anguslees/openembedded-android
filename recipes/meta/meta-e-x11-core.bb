DESCRIPTION = "Meta-package for Enlightenment/X11"
SECTION = "x11/base"
ALLOW_EMPTY = "1"
PR = "r2"
PACKAGE_ARCH = "all"
LICENSE = "MIT"

RDEPENDS_${PN} = "task-e-x11-core"

inherit meta
