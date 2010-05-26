LICENSE = "LGPL"
SECTION = "x11/libs"
DEPENDS = "glib-2.0 fontconfig freetype zlib virtual/libx11 libxft gtk-doc"
RRECOMMENDS_${PN} = "pango-module-basic-x pango-module-basic-fc"
DESCRIPTION = "The goal of the Pango project is to provide an \
Open Source framework for the layout and rendering of \
internationalized text."
PR = "r0"

# seems to go wrong with default cflags
FULL_OPTIMIZATION_arm = "-O2"

SRC_URI = "ftp://ftp.gtk.org/pub/gtk/v2.6/pango-${PV}.tar.bz2 \
	   file://no-tests.patch \
	   file://pkgconfig_fixes.patch"

inherit autotools pkgconfig

EXTRA_OECONF = "--disable-glibtest \
		--enable-explicit-deps=no \
	        --disable-debug"

FILES_${PN} = "/etc ${bindir} ${libdir}/libpango*.so.*"

LIBV = "1.4.0"

do_stage () {
	for lib in pango pangox pangoft2 pangoxft; do
		oe_libinstall -so -C pango lib$lib-1.0 ${STAGING_LIBDIR}/
	done
	install -d ${STAGING_INCDIR}/pango
	install -m 0644 ${S}/pango/pango*.h ${STAGING_INCDIR}/pango/
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

SRC_URI[md5sum] = "f5b5da7a173f0566d8217ec112fde993"
SRC_URI[sha256sum] = "4cf04489ff291f3f1835783b8cfa8347d99f6a05d7d9da21c8d737f441bea3ac"
