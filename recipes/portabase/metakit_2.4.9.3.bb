SECTION = "libs"
DESCRIPTION = "Metakit is an efficient embedded database library with a small \
footprint. It fills the gap between flat-file, relational, object-oriented, and \
tree-structured databases, supporting relational joins, serialization, nested structures,\
and instant schema evolution."
LICENSE = "MetaKit"
HOMEPAGE = "http://www.equi4.com/metakit.html"

SRC_URI = "http://www.equi4.com/pub/mk/metakit-${PV}.tar.gz \
	   file://metakit-2.4.9.3.patch"
PR = "r3"

FILES_${PN} += " ${libdir}/*.so"

do_configure_prepend() {

#	cp ${STAGING_DATADIR}/libtool/*.* ${S}/unix/scripts/

}

do_configure () {

	cd builds
	../unix/configure \
                   --build=${BUILD_SYS} \
                    --host=${HOST_SYS} \
                    --target=${TARGET_SYS} \
                    --prefix=${prefix} \
                    --exec_prefix=${exec_prefix} \
                    --bindir=${bindir} \
                    --sbindir=${sbindir} \
                    --libexecdir=${libexecdir} \
                    --datadir=${datadir} \
                    --sysconfdir=${sysconfdir} \
                    --sharedstatedir=${sharedstatedir} \
                    --localstatedir=${localstatedir} \
                    --libdir=${libdir} \
                    --includedir=${includedir} \
                    --oldincludedir=${oldincludedir} \
                    --infodir=${infodir} \
                    --mandir=${mandir} \
                        ${EXTRA_OECONF} \
                    $@;

}

do_stage() {
	oe_libinstall -a -so -C builds/.libs libmk4 ${STAGING_LIBDIR}
	install -d ${STAGING_INCDIR}/
	for X in mk4.h mk4.inl
	do
		install -m 0644 include/${X} ${STAGING_INCDIR}/${X}
	done

}

do_compile () {
	cd builds
	oe_runmake
}

do_install() {
	oe_libinstall -so -C builds/.libs libmk4 ${D}${libdir}
}


SRC_URI[md5sum] = "89a25775fee5db536937f36deb5223f6"
SRC_URI[sha256sum] = "4d6c46289b1f1dea4e8bbc78332901491bde0c552727c27431ca9cb0234658da"
