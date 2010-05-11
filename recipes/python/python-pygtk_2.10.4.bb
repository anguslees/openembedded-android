DESCRIPTION = "Python GTK+ 2.10.x Bindings"
SECTION = "devel/python"
# needs gtk+ 2.10.x
DEPENDS = "gtk+ libglade python-pycairo python-pygobject"
RDEPENDS = "python-shell python-pycairo python-pygobject"
PROVIDES = "python-pygtk2"
SRCNAME = "pygtk"
LICENSE = "LGPL"
PR = "ml12"

MAJ_VER = "${@bb.data.getVar('PV',d,1).split('.')[0]}.${@bb.data.getVar('PV',d,1).split('.')[1]}"
SRC_URI = "ftp://ftp.gnome.org/pub/gnome/sources/pygtk/${MAJ_VER}/${SRCNAME}-${PV}.tar.bz2 \
           file://fix-gtkunixprint.patch;patch=1 \
           file://prevent_to_get_display_during_import.patch;patch=1 \
           file://nodocs.patch;patch=1 \
           file://gdk_display.patch;patch=1;pnum=0 \
           file://acinclude.m4"
S = "${WORKDIR}/${SRCNAME}-${PV}"

EXTRA_OECONF = "\
  --disable-docs \
  --with-python-includes=${STAGING_INCDIR}/../ \
"

inherit autotools pkgconfig distutils-base

do_configure_prepend() {
	install -m 0644 ${WORKDIR}/acinclude.m4 ${S}/
}

# dirty fix #1: remove dependency on python-pygobject-dev
do_install_append() {
	find ${D} -name "*.la"|xargs rm -f
	rm -f ${D}/${bindir}/pygtk-codegen-2.0
	rm -rf ${D}/${libdir}/pkgconfig
}

# dirty fix #2: fix build system paths leaking in
require fix-path.inc

PACKAGES =+ "${PN}-demo"
FILES_${PN}-demo = "\
  ${bindir}/pygtk-demo \
  ${libdir}/pygtk \
"
RDEPENDS_${PN}-demo = "python-pygtk python-stringold python-lang"

# todo: revamp packaging, package demo seperatly
FILES_${PN}-dev += "\
  ${libdir}/pygtk/2.0 \
  ${bindir}/pygtk-* \
  ${datadir}/pygtk/2.0"

do_stage() {
	autotools_stage_includes
	sed -i s:${prefix}/share:${STAGING_DATADIR}: codegen/pygtk-codegen-2.0
	install -m 0755 codegen/pygtk-codegen-2.0 ${STAGING_BINDIR_NATIVE}/
	# until we have a newer pygobject version, we resue pygtk's codegen
	ln -sf ./pygtk-codegen-2.0 ${STAGING_BINDIR_NATIVE}/pygobject-codegen-2.0
	install -d ${STAGING_DATADIR}/pygtk/2.0/codegen
	install -d ${STAGING_DATADIR}/pygtk/2.0/defs/
	cp -pPr codegen/*.py* ${STAGING_DATADIR}/pygtk/2.0/codegen/
	cp -pPr *.defs ${STAGING_DATADIR}/pygtk/2.0/defs/
	cp -pPr gtk/*.defs ${STAGING_DATADIR}/pygtk/2.0/defs/
}

SRC_URI[md5sum] = "89afe242275a3fce57b4ffb5fadc5888"
SRC_URI[sha256sum] = "ab9ad4481af435894db27e78010f6bbc21c62252dbe7a254121453419edde8f5"
