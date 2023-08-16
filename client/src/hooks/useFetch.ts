import { useState, useEffect } from 'react'
import { getPlanners } from '../apis/planner'

type DatasType = {
  plannerId: number
  planTitle: string
  startDate: string
  endDate: string
  hostName: string
  hostUrl: string
}[]

type DataType = {
  content: DatasType
  currentPageNumber: number
  totalPageNumber: number
}

const useFetch = (page = 0) => {
  const [results, setResults] = useState<DatasType>([])
  const [isLoading, setIsLoading] = useState(false)
  const [hasNextPage, setHasNextPage] = useState(false)

  useEffect(() => {
    setIsLoading(true)

    const controller = new AbortController()
    const { signal } = controller

    getPlanners(page, '', { signal })
      .then(res => {
        const { content, currentPageNumber, totalPageNumber } = res?.data as DataType
        setResults(prev => [...prev, ...content])
        setHasNextPage(currentPageNumber < totalPageNumber - 1)
        setIsLoading(false)
      })
      .catch(() => {
        setIsLoading(false)
      })

    return () => controller.abort()
  }, [page])

  return { results, isLoading, hasNextPage }
}

export default useFetch
