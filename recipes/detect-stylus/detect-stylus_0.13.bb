inherit gpe pkgconfig
LICENSE = "GPL"

PR = "r3"

DEPENDS = "virtual/libx11 xcursor-transparent-theme xrdb"
SECTION = "gpe"
RDEPENDS_${PN} = "xrdb"

DESCRIPTION = "Touchscreen detection utility"

SRC_URI = "${GPE_MIRROR}/${PN}-${PV}.tar.gz \
           file://access.patch;striplevel=0 \
	   file://extra-device-check.patch \
	   file://correct-theme-name.patch"

export CVSBUILD="no"

SRC_URI[md5sum] = "e52d681c5ca11f4a9491624ab8c4fa90"
SRC_URI[sha256sum] = "e9f0cd45a82d2f60af7e927b1740b5971e9007bf7d05e39a900db2dcb7871916"
