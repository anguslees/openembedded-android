DESCRIPTION = "The portable SDK for UPnP* Devices (libupnp) provides developers with an API and open source code for building control points, devices, and bridges that are compliant with Version 1.0 of the Universal Plug and Play Device Architecture Specification."
HOMEPAGE = "http://pupnp.sourceforge.net/"
LICENSE = "BSD"

PR = "r1"

LEAD_SONAME = "libupnp"
SRC_URI = "${SOURCEFORGE_MIRROR}/pupnp/${P}.tar.bz2"

inherit autotools

AUTOTOOLS_STAGE_PKGCONFIG = "1"

do_stage() {
	autotools_stage_all
}

SRC_URI[md5sum] = "2b0370e7c405a5bc80880085e6d7d827"
SRC_URI[sha256sum] = "95b11e2db04c2e714c2f4fb05d9d31351d3388dababd4e1dd5300f4b0d5e8767"
