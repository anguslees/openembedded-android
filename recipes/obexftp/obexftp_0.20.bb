DESCRIPTION = "OBEX Ftp Client based on openobex."
SECTION = "console/network"
HOMEPAGE = "http://openobex.triq.net"
LICENSE = "GPL"
DEPENDS = "openobex libgsm"
PR = "r4"

SRC_URI = "${SOURCEFORGE_MIRROR}/openobex/obexftp-${PV}.tar.gz \
	   file://iconv.patch \
	   file://i-hate-libtool.patch \
	   file://m4.patch"

inherit autotools gettext

EXTRA_OECONF += "--enable-bluetooth --disable-swig --disable-perl --disable-python --disable-tcl --disable-builddocs --disable-rpath"

PARALLEL_MAKE = ""

do_stage() {
	autotools_stage_all
}


SRC_URI[md5sum] = "085b9edc0504c0d79e7479a54e2018c5"
SRC_URI[sha256sum] = "7246fc75257afcd30ff16dc70185057157bcefc12fa9fba111b3b201577cc40e"
