require python-efl.inc
DEPENDS += "epsilon python-ecore"
RDEPENDS_${PN} += "python-ecore"

SRCREV = "${EFL_SRCREV}"
SRC_URI = "svn://svn.enlightenment.org/svn/e/OLD/BINDINGS/python;module=${PN};proto=http"
