DESCRIPTION = "H.264 encoder"
LICENSE = "GPL"
PR = "r6"

X264PV = "snapshot-20100531-2245"

SRC_URI = "http://download.videolan.org/pub/videolan/x264/snapshots/x264-${X264PV}.tar.bz2"
SRC_URI[md5sum] = "d1ccb8122bd418291a9576a2bffdf662"
SRC_URI[sha256sum] = "929e946947701a0b3a336a4b9cfe65daf4c52480f45d4363335ae2a3d5596fa9"

S = "${WORKDIR}/${PN}-${X264PV}"

inherit autotools lib_package pkgconfig

# default --extra-cflags
X264_ECFLAGS = ""
X264_ECFLAGS_mips = "-fPIC"

# disable use of assembler written functions
X264_DISABLE_ASM = "--disable-asm"
# use assembler written functions for those archs supporting this
X264_DISABLE_ASM_x86 = ""
DEPENDS_x86 = "yasm-native"

EXTRA_OECONF = '--enable-shared ${X264_DISABLE_ASM} --extra-cflags="${X264_ECFLAGS}"'


