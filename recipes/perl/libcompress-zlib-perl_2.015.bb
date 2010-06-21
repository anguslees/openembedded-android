DESCRIPTION = "Compress::Zlib - Interface to zlib compression library"
SECTION = "libs"
LICENSE = "Artistic|GPL"
PR = "r12"
RDEPENDS_${PN} += "libio-compress-base-perl libcompress-raw-zlib-perl libio-compress-zlib-perl"

SRC_URI = "http://search.cpan.org/CPAN/authors/id/P/PM/PMQS/Compress-Zlib-${PV}.tar.gz"

S = "${WORKDIR}/Compress-Zlib-${PV}"

inherit cpan

FILES_${PN} = "${PERLLIBDIRS}/auto/Compress/Zlib/* \
               ${PERLLIBDIRS}/Compress \
               ${datadir}/perl5"

BBCLASSEXTEND="native"

SRC_URI[md5sum] = "689ba2cc399b019d0bf76a0575c32947"
SRC_URI[sha256sum] = "9b4c6fde1c972016fcbea1f019d143261ac0f5410652ea91571d7eedd22831cc"
