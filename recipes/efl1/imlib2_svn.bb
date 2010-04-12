DESCRIPTION = "A graphic library for file loading, saving, rendering, and manipulation."
LICENSE = "BSD"
# can also depend on tiff34, ungif or gif, z, bz2, id3tag
DEPENDS = "freetype libpng jpeg virtual/libx11 libxext"
PROVIDES = "virtual/imlib2"
PV = "1.4.2.001+svnr${SRCPV}"
PR = "r0"
SRCREV = "${EFL_SRCREV}"

inherit efl

EXTRA_OECONF = "--with-x"

# TODO: Use more fine granular version
#OE_LT_RPATH_ALLOW=":${libdir}/imlib2/loaders:${libdir}/imlib2/filters:"
OE_LT_RPATH_ALLOW = "any"
OE_LT_RPATH_ALLOW[export]="1"

PACKAGES =+ "imlib2-loaders-dbg imlib2-filters-dbg imlib2-loaders imlib2-filters"
FILES_${PN} = "${libdir}/lib*.so.* ${libdir}/imlib2/*/*.so"
FILES_${PN}-dbg = "${libdir}/.debug/ ${bindir}/.debug/"
FILES_${PN}-dev += "${bindir}/imlib2-config ${libdir}/*.so ${includedir}"
FILES_${PN}-bin = "${bindir}"
FILES_imlib2-loaders = "${libdir}/imlib2/loaders/*.so"
FILES_imlib2-filters = "${libdir}/imlib2/filters/*.so"
FILES_imlib2-loaders-dbg += "${libdir}/imlib2/loaders/.debug"
FILES_imlib2-filters-dbg += "${libdir}/imlib2/filters/.debug"
