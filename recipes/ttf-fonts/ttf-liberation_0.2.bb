require ttf.inc

DESCRIPTION = "Liberation fonts - TTF Version"
HOMEPAGE = "https://www.redhat.com/promo/fonts/"
LICENSE = "Liberation"
PR = "r3"

SRC_URI = "http://fedorahosted.org/liberation-fonts/export/807b6dfd069b998cd9b4d3158da98817ef23c79d/F-9/liberation-fonts-ttf-3.tar.gz"
S = "${WORKDIR}/liberation-fonts-${PV}"

PACKAGES = "${PN}-dbg ttf-liberation-mono ttf-liberation-sans ttf-liberation-serif"
RRECOMMENDS_${PN}-dbg = ""

FILES_ttf-liberation-mono  = "${datadir}/fonts/truetype/*Mono*"
FILES_ttf-liberation-sans  = "${datadir}/fonts/truetype/*Sans*"
FILES_ttf-liberation-serif = "${datadir}/fonts/truetype/*Serif*"

SRC_URI[md5sum] = "77728078a17e39f7c242b42c3bf6feb8"
SRC_URI[sha256sum] = "174cf27c57612971434ec8cc4a52bfd37bad8408e9b9219539c6d5113df6ff8f"
