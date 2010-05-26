SECTION = "console/network"
DEPENDS = "virtual/db"
DESCRIPTION = "Generic client/server library for SASL authentication."
LICENSE = "BSD"

PR = "r1"

SRC_URI = "ftp://ftp.andrew.cmu.edu/pub/cyrus-mail/OLD-VERSIONS/sasl/cyrus-sasl-${PV}.tar.gz \
	   file://configure.patch"

inherit autotools

acpaths = "-I ${S}/cmulocal -I ${S}/config -I ."
CFLAGS_append = " -I${S}/include -I${S}/saslauthd/include"
EXTRA_OECONF = "--enable-shared --enable-static --with-dblib=berkeley \
	        --with-bdb-libdir=${STAGING_LIBDIR} \
	        --with-bdb-incdir=${STAGING_INCDIR} \
		--without-opie --without-des"

do_configure_prepend () {
	rm -f acinclude.m4 config/libtool.m4
}

do_compile_prepend () {
	cd include
	${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS} makemd5.c -o makemd5
	touch makemd5.o makemd5.lo makemd5
	cd ..
}

do_stage () {
	oe_libinstall -so -a -C lib libsasl2 ${STAGING_LIBDIR}
	install -d ${STAGING_LIBDIR}/sasl
	install -d ${STAGING_INCDIR}/sasl
	install -m 0644 ${S}/include/hmac-md5.h ${STAGING_INCDIR}/sasl/
	install -m 0644 ${S}/include/md5.h ${STAGING_INCDIR}/sasl/
	install -m 0644 ${S}/include/md5global.h ${STAGING_INCDIR}/sasl/
	install -m 0644 ${S}/include/sasl.h ${STAGING_INCDIR}/sasl/
	install -m 0644 ${S}/include/saslplug.h ${STAGING_INCDIR}/sasl/
	install -m 0644 ${S}/include/saslutil.h ${STAGING_INCDIR}/sasl/
	install -m 0644 ${S}/include/prop.h ${STAGING_INCDIR}/sasl/
}

SRC_URI[md5sum] = "4add6be2f194dc51aafc64193a1dd77b"
SRC_URI[sha256sum] = "7a0b1d5135fa470d10b86f4efbf3f59d6412f1e539f7ea61604d44ac4505dba6"
