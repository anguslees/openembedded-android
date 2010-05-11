DESCRIPTION = "Library to read the extended image information (EXIF) from JPEG pictures"
HOMEPAGE = "http://sourceforge.net/projects/libexif"
LICENSE = "LGPL"
SECTION = "libs"
PR = "r2"

SRC_URI = "${SOURCEFORGE_MIRROR}/libexif/libexif-${PV}.tar.gz"

inherit autotools pkgconfig

do_stage() {
	oe_libinstall -a -so -C libexif libexif ${STAGING_LIBDIR}

	install -d ${STAGING_INCDIR}/libexif
	for X in exif-byte-order.h exif-data.h exif-format.h exif-loader.h exif-tag.h exif-content.h exif-entry.h exif-ifd.h exif-result.h exif-utils.h exif-log.h exif-mnote-data.h _stdint.h
	do
		install -m 0644 ${S}/libexif/$X ${STAGING_INCDIR}/libexif/$X
	done
}

SRC_URI[md5sum] = "0aa142335a8a00c32bb6c7dbfe95fc24"
SRC_URI[sha256sum] = "a2f309c702ee72967676d96b1a9d06806d069fde8e88076200cca6479c3d3c38"
