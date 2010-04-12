DESCRIPTION = "HTML Tagset bits."
SECTION = "libs"
LICENSE = "Artistic|GPL"
PR = "r2"

SRC_URI = "http://search.cpan.org/CPAN/authors/id/P/PE/PETDANCE/HTML-Tagset-${PV}.tar.gz"

S = "${WORKDIR}/HTML-Tagset-${PV}"

inherit cpan

BBCLASSEXTEND="native"

PACKAGE_ARCH = "all"
