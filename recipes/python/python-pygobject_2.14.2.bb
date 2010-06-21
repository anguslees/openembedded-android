DESCRIPTION = "Python GObject bindings"
SECTION = "devel/python"
LICENSE = "LGPL"
DEPENDS = "python-pygobject-native"
PE = "1"
PR = "r3"

MAJ_VER = "${@bb.data.getVar('PV',d,1).split('.')[0]}.${@bb.data.getVar('PV',d,1).split('.')[1]}"

SRC_URI = "\
  ftp://ftp.gnome.org/pub/GNOME/sources/pygobject/${MAJ_VER}/pygobject-${PV}.tar.bz2 \
  file://python-path.patch \
  file://generate-constants.patch \
"
S = "${WORKDIR}/pygobject-${PV}"

inherit autotools distutils-base pkgconfig

EXTRA_OECONF += '--with-python-includes="${STAGING_INCDIR}/.."'

do_stage() {
	autotools_stage_all
	install -d ${STAGING_LIBDIR}/../share/pygobject/
	cp -dpfR docs/* ${STAGING_LIBDIR}/../share/pygobject/
	install -d ${STAGING_LIBDIR}/../share/gtk-doc/html/pygobject/
	cp docs/style.css ${STAGING_LIBDIR}/../share/gtk-doc/html/pygobject/
}

FILES_${PN} = "${libdir}/python*"
FILES_${PN}-dev += "${datadir}/pygobject/xsl"

SRC_URI[md5sum] = "0e9e9300e81847f4f7266f49d3bebbaf"
SRC_URI[sha256sum] = "79c5d3ab8f072f1d0b102b2bd6410b0dc383a008b5efad1750d8b6dadfde8c6e"
