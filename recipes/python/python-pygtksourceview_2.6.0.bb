DESCRIPTION = "Python gtksourceview Bindings"
SECTION = "devel/python"
HOMEPAGE = "http://projects.gnome.org/gtksourceview/pygtksourceview.html"
PRIORITY = "optional"
DEPENDS = "gtksourceview2 python-pygtk"
SRCNAME = "pygtksourceview"
LICENSE = "LGPL"
PR = "r0"

MAJ_VER = "${@bb.data.getVar('PV',d,1).split('.')[0]}.${@bb.data.getVar('PV',d,1).split('.')[1]}"
SRC_URI = "ftp://ftp.gnome.org/pub/gnome/sources/pygtksourceview/${MAJ_VER}/${SRCNAME}-${PV}.tar.bz2"

S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit autotools pkgconfig distutils-base

EXTRA_OECONF += "--with-python-includes=${STAGING_INCDIR}/../"


SRC_URI[md5sum] = "5587a1865bd5c785c6f34095c57cc96b"
SRC_URI[sha256sum] = "77acb735fee997a1638c79256b9dcf592566d066b54a72eb4321909f98f66178"
