LICENSE = "LGPL"
SECTION = "x11/libs"
DEPENDS = "glib-2.0 fontconfig freetype zlib virtual/libx11 libxft gtk-doc cairo"
DESCRIPTION = "The goal of the Pango project is to provide an \
Open Source framework for the layout and rendering of \
internationalized text."
PR = "r2"

RRECOMMENDS_${PN} = "pango-module-basic-x pango-module-basic-fc"

# seems to go wrong with default cflags
FULL_OPTIMIZATION_arm = "-O2"

SRC_URI = "ftp://ftp.gtk.org/pub/gtk/v2.8/pango-${PV}.tar.bz2 \
	   file://no-tests.patch"

inherit autotools pkgconfig

EXTRA_OECONF = "--disable-glibtest \
		--enable-explicit-deps=no \
	        --disable-debug"

LEAD_SONAME = "libpango-1.0*"
LIBV = "1.4.0"

FILES_${PN} = "/etc ${bindir}/* ${libdir}/libpango*.so.*"
FILES_${PN}-dbg += "${libdir}/pango/${LIBV}/modules/.debug"
FILES_${PN}-dev += "${libdir}/pango/${LIBV}/modules/*.la"

do_stage () {
autotools_stage_all
}

postinst_prologue() {
if [ "x$D" != "x" ]; then
  exit 1
fi

}

PACKAGES_DYNAMIC = "pango-module-*"

python populate_packages_prepend () {
	prologue = bb.data.getVar("postinst_prologue", d, 1)

	modules_root = bb.data.expand('${libdir}/pango/${LIBV}/modules', d)

	do_split_packages(d, modules_root, '^pango-(.*)\.so$', 'pango-module-%s', 'Pango module %s', prologue + 'pango-querymodules > /etc/pango/pango.modules')
}

SRC_URI[md5sum] = "7302220d93ac17d2c44f356d852e81dc"
SRC_URI[sha256sum] = "677d3a943cfeedebf138024de6e4a773f7f6626236613401f3a797f82f04eba7"
