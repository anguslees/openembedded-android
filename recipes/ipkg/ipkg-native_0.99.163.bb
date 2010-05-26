S = "${WORKDIR}/ipkg-${PV}"

#require ipkg_${PV}.bb
require ipkg-native.inc
PR = "r6"

inherit autotools pkgconfig native

SRC_URI = "http://www.handhelds.org/pub/packages/ipkg/ipkg-${PV}.tar.gz \
           file://update_version_comparision.patch \
           file://enable_debversion.patch \
           file://is-processing.patch \
           file://1-pkg-parse--Optimize-inefficient-parsing.patch \
           file://2-pkg-vec--Optimize-gross-inefficiency.patch \
          "


SRC_URI[md5sum] = "0b10ad2924611bccaea8ddf98481a192"
SRC_URI[sha256sum] = "405743e1ba320ed6095d42a898f1486e3af1d745cae2bd45dfe1d8403a4d8995"
