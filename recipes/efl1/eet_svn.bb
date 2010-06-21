DESCRIPTION = "EET is the Enlightenment data storage library"
DEPENDS = "pkgconfig zlib jpeg openssl eina"
LICENSE = "MIT BSD"
SRCREV = "${EFL_SRCREV}"
PV = "1.3.0+svnr${SRCPV}"

inherit efl

BBCLASSEXTEND="native"

EXTRA_OECONF = "\
  --enable-openssl \
  --enable-cypher \
  --enable-signature \
  --disable-coverage \
  --enable-old-eet-file-format \
  --disable-assert \
"
