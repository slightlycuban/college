import math

#compute the number of iterations need for bisection
def num( ):
	return math.ceil(math.log10(1/.000001)/math.log10(2))

#function equals f(x) = x^4 -3x-3
def computeFunction( input ):
	return (math.pow(input, 4) - (3 * math.pow(input,2)) - 3)

def bisection( a, b ):
	counter = math.trunc(num())
	p = .5 * ( a + b)
	tempa = 0
	tempp = 0

	for i in range(0,counter):
		#magic happens here
		tempa = computeFunction(a)
		tempp = computeFunction(p)
		
		if tempa * tempp < 0:
			#is this by value?
			b = p
			p = .5 * (a + b)
		else:
			a = p
			p = .5 * (a + b)
	return p

#the derivative equals 4x^3 - 6x
def computeDerivative( input ):
	return (4 * math.pow(input, 3) - 6 * input)					
if __name__ == "__main__":
	print  bisection( 1, 2)
	print computeDerivative(1)
