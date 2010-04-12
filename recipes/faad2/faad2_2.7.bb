DESCRIPTION = "Library for reading some sort of media format."
SECTION = "libs"
LICENSE = "LGPL"
PRIORITY = "optional"

inherit autotools

SRC_URI = "${SOURCEFORGE_MIRROR}/faac/faad2-src/faad2-${PV}/${PN}-${PV}.tar.bz2;name=faad2 \
"

SRC_URI[faad2.md5sum] = "4c332fa23febc0e4648064685a3d4332"
SRC_URI[faad2.sha256sum] = "14561b5d6bc457e825bfd3921ae50a6648f377a9396eaf16d4b057b39a3f63b5"

PACKAGES =+ "libfaad libfaad-dev libmp4ff libmp4ff-dev"

FILES_${PN} = "${bindir}/faad"

FILES_libfaad = "${libdir}/libfaad.so.*"
FILES_libfaad-dev = "${libdir}/libfaad.so ${libdir}/libfaad.la ${libdir}/libfaad.a ${includedir}/faad.h"

FILES_libmp4ff = "${libdir}/libmp4ff.so.*"
FILES_libmp4ff-dev = "${libdir}/libmp4ff.so ${libdir}/libmp4ff.la ${libdir}/libmp4ff.a ${includedir}/mp4ff.h"

