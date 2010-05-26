LICENSE = "GPL"
PR = "r2"
DESCRIPTION = "CORBA ORB"
SECTION = "x11/gnome/libs"
SRC_URI = "http://ftp.gnome.org/pub/GNOME/sources/ORBit2/2.13/ORBit2-${PV}.tar.bz2 \
           file://configure-lossage.patch \
	   file://gtk-doc.m4 \
	   file://gtk-doc.make"
DEPENDS = "libidl popt orbit2-native gtk-doc"

FILES_${PN} += "${libdir}/orbit-2.0/*.so"

S = "${WORKDIR}/ORBit2-${PV}"

inherit autotools pkgconfig
PARALLEL_MAKE = ""
EXTRA_OECONF = "--disable-gtk-doc"
EXTRA_OEMAKE = "IDL_COMPILER='${STAGING_BINDIR_NATIVE}/orbit-idl-2'"

do_configure_prepend() {
	mkdir -p m4
	install ${WORKDIR}/gtk-doc.m4 ./m4/
	install ${WORKDIR}/gtk-doc.make ./
}

do_compile_append () {
	sed 's:^orbit_idl=.*/:orbit_idl=${STAGING_BINDIR_NATIVE}/:' < ORBit-2.0.pc > ORBit-2.0.pc.new
	mv ORBit-2.0.pc.new ORBit-2.0.pc
}

do_stage() {
	oe_libinstall -so -C src/orb libORBit-2 ${STAGING_LIBDIR}
	oe_libinstall -so -C src/services/name libORBitCosNaming-2 ${STAGING_LIBDIR}
	oe_libinstall -so -C src/services/imodule libORBit-imodule-2 ${STAGING_LIBDIR}
	install -m 0644 src/services/name/libname-server-2.a ${STAGING_LIBDIR}/

	for dir in orbit orbit/poa orbit/orb-core orbit/util orbit/dynamic; do
		install -d ${STAGING_INCDIR}/orbit-2.0/$dir
		( cd include/$dir; for i in *.h; do install -m 0644 $i ${STAGING_INCDIR}/orbit-2.0/$dir/$i; done )
	done

	install -d ${STAGING_INCDIR}/orbit-2.0/ORBitservices
	install -m 0644 src/services/name/CosNaming.h ${STAGING_INCDIR}/orbit-2.0/ORBitservices/
	install -m 0644 src/services/name/CosNaming_impl.h ${STAGING_INCDIR}/orbit-2.0/ORBitservices/
	install -m 0644 src/services/imodule/orbit-imodule.h ${STAGING_INCDIR}/orbit-2.0/ORBitservices/
}

SRC_URI[md5sum] = "8d1e654f9b7e1399dc98da3bb4b96762"
SRC_URI[sha256sum] = "fc87440d252c77f5c1afc17bb3015586cca264bc5fa8794bc54b869b8106265d"
