import { useCallback, useRef, useState, RefCallback } from 'react'
import { Link } from 'react-router-dom'
import PlannerTitle from '../components/Feed/PlannerTitle'
import SearchBar from '../components/Feed/SearchBar'
import styles from './Feed.module.scss'
import useInfiniteScrolling from '../hooks/useInfiniteScrolling'

export default function Feed() {
  const [page, setPage] = useState(0)
  const [query, setQuery] = useState('')
  const { results, isLoading, isError, hasNextPage } = useInfiniteScrolling(page, query)

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault()

    const formData = new FormData(event.currentTarget)
    const text = formData.get('text') as string
    setPage(0)
    setQuery(text)
  }

  const intObserver = useRef<IntersectionObserver | null>(null)
  const lastPostRef: RefCallback<HTMLLIElement> = useCallback(
    (lastElement: HTMLElement | null) => {
      if (isLoading) return
      if (intObserver.current) intObserver.current.disconnect()

      intObserver.current = new IntersectionObserver(posts => {
        if (posts[0].isIntersecting && hasNextPage) {
          setPage(currentPage => currentPage + 1)
        }
      })

      if (lastElement) intObserver.current.observe(lastElement)
    },
    [isLoading, hasNextPage]
  )

  return (
    <div className={styles.container}>
      <h1 className={styles.headline}>다른 사람들의 여행 계획을 둘러보세요!</h1>
      <div className={styles.line} />
      <SearchBar handleSubmit={handleSubmit} />
      <ul className={styles.ul}>
        {results.map((data, idx) => {
          return (
            <Link to={`/planner/${data.plannerId}`} key={data.plannerId}>
              <PlannerTitle {...data} ref={idx === results.length - 1 ? lastPostRef : undefined} />
            </Link>
          )
        })}
        {isLoading && <div>loading ...</div>}
        {isError && (
          <div className={styles.errorMessage}>
            데이터를 불러오는데 실패했습니다. <br /> 잠시 후 다시 시도해주세요
          </div>
        )}
      </ul>
    </div>
  )
}
