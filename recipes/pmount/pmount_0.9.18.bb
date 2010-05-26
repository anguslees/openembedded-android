DESCRIPTION = "Policy based mounter that gives the ability to mount removable devices as a user"
HOMEPAGE = "http://pmount.alioth.debian.org/"
LICENSE = "GPLv2"
PR = "r1"

DEPENDS = "hal util-linux-ng sysfsutils"
RDEPENDS_${PN}-hal = "${PN}"
RRECOMMENDS_${PN}-hal = "hal"

SRC_URI = "http://alioth.debian.org/frs/download.php/2624/${P}.tar.gz \
	   file://gettext.patch \
	   file://install.patch \
	  "

inherit autotools gettext

EXTRA_OECONF = "--enable-hal"

PACKAGES =+ "${PN}-hal"

FILES_${PN}-hal = "${bindir}/pmount-hal"


SRC_URI[md5sum] = "d04973bde34edac7dd2e50bfe8f10700"
SRC_URI[sha256sum] = "b216936e6e011b58fefee8e3f80d01008cb078b7fe2a5632b2ce98bc7bcb05c2"
