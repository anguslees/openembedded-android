require intltool.inc

#RDEPENDS_${PN} = "libxml-parser-perl"
RRECOMMENDS_${PN} = "perl-modules"

PR = "${INC_PR}.1"

SRC_URI[md5sum] = "95c4bd2a91419083ee880a3f53f86edf"
SRC_URI[sha256sum] = "4ebece4bb752e22b2f15a9fe24e83aec59a3a41b67a9fa9ffd6b805c519e90ba"
